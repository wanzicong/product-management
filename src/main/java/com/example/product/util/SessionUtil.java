package com.example.product.util;

import com.example.product.common.Constants;
import com.example.product.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Session 工具类
 */
public class SessionUtil {

    /**
     * 获取当前请求
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取当前Session
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request != null ? request.getSession() : null;
    }

    /**
     * 设置登录用户
     */
    public static void setLoginUser(User user) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(Constants.SESSION_USER, user);
        }
    }

    /**
     * 获取登录用户
     */
    public static User getLoginUser() {
        HttpSession session = getSession();
        if (session != null) {
            return (User) session.getAttribute(Constants.SESSION_USER);
        }
        return null;
    }

    /**
     * 移除登录用户
     */
    public static void removeLoginUser() {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(Constants.SESSION_USER);
            session.invalidate();
        }
    }

    /**
     * 获取客户端IP
     */
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理时，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
