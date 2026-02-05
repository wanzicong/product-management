package com.example.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.OperationLog;

/**
 * 操作日志 Service
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 分页查询日志
     */
    IPage<OperationLog> pageLog(Integer pageNum, Integer pageSize, String username, String module);

    /**
     * 记录日志
     */
    void saveLog(String module, String operation, String method, String params);
}
