package com.example.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.common.Result;
import com.example.product.entity.OperationLog;
import com.example.product.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志控制器
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 日志列表页面
     */
    @GetMapping("/list")
    public String list() {
        return "log/list";
    }

    /**
     * 分页查询日志数据
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<IPage<OperationLog>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String module) {
        IPage<OperationLog> page = operationLogService.pageLog(pageNum, pageSize, username, module);
        return Result.success(page);
    }
}
