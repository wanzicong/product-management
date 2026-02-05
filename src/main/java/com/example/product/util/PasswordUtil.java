package com.example.product.util;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 密码工具类
 */
public class PasswordUtil {

    /**
     * 加密密码
     */
    public static String encrypt(String password) {
        return DigestUtil.md5Hex(password);
    }

    /**
     * 验证密码
     */
    public static boolean verify(String rawPassword, String encodedPassword) {
        return encrypt(rawPassword).equals(encodedPassword);
    }
}
