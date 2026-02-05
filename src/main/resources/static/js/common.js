// 公共 JavaScript

// 显示成功提示
function showSuccess(message) {
    showAlert(message, 'success');
}

// 显示错误提示
function showError(message) {
    showAlert(message, 'danger');
}

// 显示警告提示
function showWarning(message) {
    showAlert(message, 'warning');
}

// 显示提示框
function showAlert(message, type) {
    const alertHtml = `
        <div class="alert alert-${type} alert-dismissible fade show position-fixed"
             style="top: 80px; right: 20px; z-index: 9999; min-width: 300px;" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    const alertElement = $(alertHtml);
    $('body').append(alertElement);
    setTimeout(() => {
        alertElement.alert('close');
    }, 3000);
}

// 确认对话框
function confirmDialog(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// 格式化日期时间
function formatDateTime(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 格式化金额
function formatMoney(amount) {
    if (amount === null || amount === undefined) return '-';
    return '¥' + parseFloat(amount).toFixed(2);
}

// 获取状态标签
function getStatusBadge(status) {
    if (status === 1) {
        return '<span class="badge bg-success">上架</span>';
    } else {
        return '<span class="badge bg-secondary">下架</span>';
    }
}

// 获取库存类型标签
function getStockTypeBadge(type) {
    if (type === 1) {
        return '<span class="badge bg-success">入库</span>';
    } else {
        return '<span class="badge bg-danger">出库</span>';
    }
}

// 生成分页HTML
function generatePagination(current, total, callback) {
    if (total <= 1) return '';

    let html = '<nav><ul class="pagination pagination-sm justify-content-center">';

    // 上一页
    if (current > 1) {
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="${callback}(${current - 1})">上一页</a></li>`;
    } else {
        html += '<li class="page-item disabled"><span class="page-link">上一页</span></li>';
    }

    // 页码
    let start = Math.max(1, current - 2);
    let end = Math.min(total, current + 2);

    if (start > 1) {
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="${callback}(1)">1</a></li>`;
        if (start > 2) {
            html += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
    }

    for (let i = start; i <= end; i++) {
        if (i === current) {
            html += `<li class="page-item active"><span class="page-link">${i}</span></li>`;
        } else {
            html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="${callback}(${i})">${i}</a></li>`;
        }
    }

    if (end < total) {
        if (end < total - 1) {
            html += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="${callback}(${total})">${total}</a></li>`;
    }

    // 下一页
    if (current < total) {
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="${callback}(${current + 1})">下一页</a></li>`;
    } else {
        html += '<li class="page-item disabled"><span class="page-link">下一页</span></li>';
    }

    html += '</ul></nav>';
    return html;
}

// AJAX 请求封装
function ajaxRequest(url, method, data, successCallback, errorCallback) {
    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        data: data ? JSON.stringify(data) : null,
        success: function(res) {
            if (res.code === 200) {
                if (successCallback) successCallback(res);
            } else {
                showError(res.message || '操作失败');
                if (errorCallback) errorCallback(res);
            }
        },
        error: function(xhr) {
            showError('请求失败，请稍后重试');
            if (errorCallback) errorCallback(xhr);
        }
    });
}
