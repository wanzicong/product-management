package com.example.product.config;

import com.example.product.common.BusinessException;
import com.example.product.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Object handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("业务异常: {}", e.getMessage());
        if (isAjaxRequest(request)) {
            return Result.error(e.getCode(), e.getMessage());
        }
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("message", e.getMessage());
        return mv;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: ", e);
        if (isAjaxRequest(request)) {
            return Result.error("系统异常，请稍后重试");
        }
        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("message", "系统异常，请稍后重试");
        return mv;
    }

    /**
     * 判断是否为Ajax请求
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }
}
