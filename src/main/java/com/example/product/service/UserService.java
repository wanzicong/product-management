package com.example.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.User;

/**
 * 用户 Service
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    User login(String username, String password);

    /**
     * 根据用户名查询
     */
    User getByUsername(String username);
}
