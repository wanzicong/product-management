package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.entity.OperationLog;
import com.example.product.entity.User;
import com.example.product.mapper.OperationLogMapper;
import com.example.product.service.OperationLogService;
import com.example.product.util.SessionUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 操作日志 Service 实现
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public IPage<OperationLog> pageLog(Integer pageNum, Integer pageSize, String username, String module) {
        Page<OperationLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like(OperationLog::getUsername, username);
        }
        if (StringUtils.hasText(module)) {
            wrapper.eq(OperationLog::getModule, module);
        }
        wrapper.orderByDesc(OperationLog::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @Async
    public void saveLog(String module, String operation, String method, String params) {
        User loginUser = SessionUtil.getLoginUser();
        OperationLog log = new OperationLog();
        if (loginUser != null) {
            log.setUserId(loginUser.getId());
            log.setUsername(loginUser.getUsername());
        }
        log.setModule(module);
        log.setOperation(operation);
        log.setMethod(method);
        log.setParams(params);
        log.setIp(SessionUtil.getClientIp());
        save(log);
    }
}
