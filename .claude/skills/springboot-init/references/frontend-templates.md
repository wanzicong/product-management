# 前端模板参考

## 页面结构模板

### 基础页面结构
```html
<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta th:replace="~{layout/head :: head}" />
    <title>页面标题</title>
</head>
<body>
    <div th:replace="~{layout/sidebar :: sidebar}"></div>

    <div class="main-content">
        <div th:replace="~{layout/header :: header}"></div>

        <div class="content-wrapper">
            <!-- 页面内容 -->
        </div>
    </div>
</body>
</html>
```

## 列表页模板

### search-panel - 搜索面板
```html
<div class="search-panel">
    <form id="searchForm" class="row g-3">
        <div class="col-md-3">
            <input type="text" class="form-control" name="keyword" placeholder="关键字搜索">
        </div>
        <div class="col-md-2">
            <button type="button" class="btn btn-primary" onclick="search()">
                <i class="bi bi-search"></i> 搜索
            </button>
            <button type="button" class="btn btn-secondary" onclick="resetSearch()">
                <i class="bi bi-arrow-counterclockwise"></i> 重置
            </button>
        </div>
        <div class="col-md-7 text-end">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="bi bi-plus-lg"></i> 新增
            </button>
            <button type="button" class="btn btn-danger" onclick="batchDelete()">
                <i class="bi bi-trash"></i> 批量删除
            </button>
        </div>
    </form>
</div>
```

### table - 数据表格
```html
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th width="50"><input type="checkbox" id="checkAll"></th>
                <th>ID</th>
                <th>名称</th>
                <th>状态</th>
                <th>创建时间</th>
                <th width="200">操作</th>
            </tr>
        </thead>
        <tbody id="tableBody"></tbody>
    </table>
</div>

<nav aria-label="分页导航">
    <ul class="pagination justify-content-center" id="pagination"></ul>
</nav>
```

## 表单页模板

### 新增/编辑表单
```html
<div class="form-container">
    <form id="editForm" class="needs-validation" novalidate>
        <input type="hidden" id="id" name="id">

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="fieldName" class="form-label">字段名称 <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="fieldName" name="fieldName" required>
                    <div class="invalid-feedback">请输入字段名称</div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <label for="status" class="form-label">状态</label>
                    <select class="form-select" id="status" name="status">
                        <option value="1">启用</option>
                        <option value="0">禁用</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-check-lg"></i> 保存
            </button>
            <a href="/{模块}/list" class="btn btn-secondary">
                <i class="bi bi-x-lg"></i> 取消
            </a>
        </div>
    </form>
</div>
```

## 常用组件

### 状态徽章
```html
<span th:if="${item.status == 1}" class="badge bg-success">启用</span>
<span th:if="${item.status == 0}" class="badge bg-danger">禁用</span>
```

### 操作按钮组
```html
<div class="btn-group btn-group-sm">
    <button class="btn btn-primary" th:onclick="'edit(' + ${item.id} + ')'">
        <i class="bi bi-pencil"></i> 编辑
    </button>
    <button class="btn btn-danger" th:onclick="'deleteItem(' + ${item.id} + ')'">
        <i class="bi bi-trash"></i> 删除
    </button>
</div>
```

### 下拉选择
```html
<select class="form-select" id="categoryId" name="categoryId" required>
    <option value="">请选择分类</option>
    <option th:each="category : ${categories}"
            th:value="${category.id}"
            th:text="${category.name}"
            th:selected="${item.categoryId == category.id}">
    </option>
</select>
```

## JS 函数模板

### 表格渲染
```javascript
function renderTable(data) {
    const html = data.map(item => `
        <tr>
            <td><input type="checkbox" class="row-checkbox" value="${item.id}"></td>
            <td>${item.id}</td>
            <td>${item.name || ''}</td>
            <td>${item.status === 1 ? '<span class="badge bg-success">启用</span>' : '<span class="badge bg-danger">禁用</span>'}</td>
            <td>${formatTime(item.createTime)}</td>
            <td>
                <button class="btn btn-sm btn-primary" onclick="edit(${item.id})">编辑</button>
                <button class="btn btn-sm btn-danger" onclick="deleteItem(${item.id})">删除</button>
            </td>
        </tr>
    `).join('');
    $('#tableBody').html(html || '<tr><td colspan="10" class="text-center">暂无数据</td></tr>');
}
```

### 分页渲染
```javascript
function renderPagination(total, pages, current) {
    let html = '';

    // 上一页
    html += `<li class="page-item ${current === 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" onclick="loadPage(${current - 1}); return false;">上一页</a>
    </li>`;

    // 页码
    for (let i = 1; i <= pages; i++) {
        html += `<li class="page-item ${i === current ? 'active' : ''}">
            <a class="page-link" href="#" onclick="loadPage(${i}); return false;">${i}</a>
        </li>`;
    }

    // 下一页
    html += `<li class="page-item ${current === pages ? 'disabled' : ''}">
        <a class="page-link" href="#" onclick="loadPage(${current + 1}); return false;">下一页</a>
    </li>`;

    $('#pagination').html(html);
}
```

### 表单提交
```javascript
$('#editForm').on('submit', function(e) {
    e.preventDefault();

    if (!this.checkValidity()) {
        e.stopPropagation();
        $(this).addClass('was-validated');
        return;
    }

    const data = $(this).serialize();
    const url = $('#id').val() ? '/{模块}/update' : '/{模块}/save';

    $.post(url, data, function(res) {
        if (res.code === 200) {
            showMessage('success', '保存成功');
            location.href = '/{模块}/list';
        } else {
            showMessage('error', res.message);
        }
    });
});
```

### 删除确认
```javascript
function deleteItem(id) {
    if (confirm('确定要删除吗？')) {
        $.post('/{模块}/delete/' + id, function(res) {
            if (res.code === 200) {
                showMessage('success', '删除成功');
                loadPage(currentPage);
            } else {
                showMessage('error', res.message);
            }
        });
    }
}
```

## 样式规范

### custom.css 颜色变量
```css
:root {
    --primary-color: #4361ee;
    --success-color: #06d6a0;
    --danger-color: #ef476f;
    --warning-color: #ffd166;
    --info-color: #118ab2;
    --gray-light: #f8f9fa;
    --gray-medium: #6c757d;
    --gray-dark: #343a40;
}
```

### 按钮样式
```css
.btn {
    padding: 0.5rem 1rem;
    border-radius: 6px;
    font-weight: 500;
    transition: all 0.2s;
}

.btn-primary {
    background: var(--primary-color);
    border-color: var(--primary-color);
}

.btn-success {
    background: var(--success-color);
    border-color: var(--success-color);
}

.btn-danger {
    background: var(--danger-color);
    border-color: var(--danger-color);
}
```

## Bootstrap 版本

项目使用 Bootstrap 5.3.2，主要引入：

```html
<!-- CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.css" rel="stylesheet">

<!-- JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
```
