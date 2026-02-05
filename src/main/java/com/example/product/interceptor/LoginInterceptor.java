package com.example.product.interceptor;

import com.example.product.entity.User;
import com.example.product.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User loginUser = SessionUtil.getLoginUser();
        if (loginUser == null) {
            // 未登录，重定向到登录页
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
