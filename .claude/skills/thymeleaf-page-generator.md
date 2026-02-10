---
name: thymeleaf-page-generator
description: 为Spring Boot项目生成Thymeleaf CRUD页面。包含列表页、新增/编辑弹窗、删除确认等功能。使用此技能时，需要提供模块名称、字段信息和页面标题。
---

基于当前项目的UI风格和代码规范，生成符合项目标准的Thymeleaf页面。

## 技术栈
- Bootstrap 5.3.2
- Bootstrap Icons 1.11.2
- Thymeleaf 3.1.x
- jQuery (通过CDN)

## 生成规则
1. **模板位置**: `src/main/resources/templates/{模块名}/`
2. **命名规范**: `{模块名}.html`
3. **布局继承**: 使用 `layout/main.html` 作为基础布局

## 页面结构模板

```html
<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta th:replace="~{layout/head :: head}" />
    <title>{页面标题}</title>
</head>
<body>
    <!-- 侧边栏 -->
    <div th:replace="~{layout/sidebar :: sidebar}"></div>

    <!-- 主内容区 -->
    <div class="main-content">
        <!-- 顶部导航 -->
        <div th:replace="~{layout/header :: header}"></div>

        <!-- 页面内容 -->
        <div class="content-wrapper">
            <div class="page-header">
                <h1>{页面标题}</h1>
            </div>

            <!-- 搜索表单 -->
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

            <!-- 数据表格 -->
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="checkAll"></th>
                            <th>字段1</th>
                            <th>字段2</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <!-- 数据行由JS动态生成 -->
                    </tbody>
                </table>
            </div>

            <!-- 分页 -->
            <nav aria-label="分页导航">
                <ul class="pagination justify-content-center" id="pagination"></ul>
            </nav>
        </div>
    </div>

    <!-- 新增/编辑模态框 -->
    <div class="modal fade" id="editModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitle">新增</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="editForm">
                        <input type="hidden" id="editId">
                        <!-- 表单字段 -->
                        <div class="mb-3">
                            <label for="fieldName" class="form-label">字段名</label>
                            <input type="text" class="form-control" id="fieldName" name="fieldName" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="save()">保存</button>
                </div>
            </div>
        </div>
    </div>

    <script th:src="@{/js/{模块名}.js}"></script>
</body>
</html>
```

## JS文件模板

```javascript
// 页面加载完成后执行
$(document).ready(function() {
    loadPage(1);
});

// 当前页码
let currentPage = 1;
let pageSize = 10;

// 加载分页数据
function loadPage(page) {
    currentPage = page;
    const params = $('#searchForm').serialize() + '&pageNum=' + page + '&pageSize=' + pageSize;

    $.get('/api/{模块名}/page?' + params, function(res) {
        if (res.code === 200) {
            renderTable(res.data.records);
            renderPagination(res.data.total, res.data.pages, page);
        } else {
            showMessage('error', res.message);
        }
    });
}

// 渲染表格
function renderTable(data) {
    const html = data.map(item => `
        <tr>
            <td><input type="checkbox" class="row-checkbox" value="${item.id}"></td>
            <td>${item.fieldName || ''}</td>
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

// 保存
function save() {
    const data = $('#editForm').serialize();
    const url = $('#editId').val() ? '/api/{模块名}/update' : '/api/{模块名}/add';

    $.post(url, data, function(res) {
        if (res.code === 200) {
            showMessage('success', '保存成功');
            $('#editModal').modal('hide');
            loadPage(currentPage);
        } else {
            showMessage('error', res.message);
        }
    });
}

// 编辑
function edit(id) {
    $.get('/api/{模块名}/' + id, function(res) {
        if (res.code === 200) {
            $('#modalTitle').text('编辑');
            $('#editId').val(res.data.id);
            // 填充表单
            $('#editModal').modal('show');
        }
    });
}

// 删除
function deleteItem(id) {
    if (confirm('确定要删除吗？')) {
        $.ajax({
            url: '/api/{模块名}/' + id,
            type: 'DELETE',
            success: function(res) {
                if (res.code === 200) {
                    showMessage('success', '删除成功');
                    loadPage(currentPage);
                }
            }
        });
    }
}
```

## 使用说明
调用此技能时，请提供以下信息：
1. **模块名称**: 如 product、category、brand
2. **页面标题**: 如"商品管理"、"分类管理"
3. **字段信息**: 需要显示和编辑的字段列表
4. **特殊功能**: 是否需要额外的自定义功能

## 注意事项
- 页面继承项目统一的布局模板
- 使用 Bootstrap 5 组件保持UI一致性
- AJAX请求使用 jQuery
- 分页使用统一的 `pageNum` 和 `pageSize` 参数
- 操作结果使用 `showMessage()` 函数提示
- 时间格式化使用 `formatTime()` 函数（在 common.js 中定义）
