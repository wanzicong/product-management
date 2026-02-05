package com.example.product.controller;

import com.example.product.common.Result;
import com.example.product.entity.User;
import com.example.product.service.UserService;
import com.example.product.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage() {
        // 已登录则跳转首页
        if (SessionUtil.getLoginUser() != null) {
            return "redirect:/";
        }
        return "login";
    }

    /**
     * 执行登录
     */
    @PostMapping("/doLogin")
    @ResponseBody
    public Result<User> doLogin(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        // 存入Session
        SessionUtil.setLoginUser(user);
        return Result.success("登录成功", user);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SessionUtil.removeLoginUser();
        return "redirect:/login";
    }
}
