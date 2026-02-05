package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.common.BusinessException;
import com.example.product.entity.User;
import com.example.product.mapper.UserMapper;
import com.example.product.service.UserService;
import com.example.product.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户 Service 实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String username, String password) {
        User user = getByUsername(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!PasswordUtil.verify(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        // 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }
}
