package com.example.product.common;

/**
 * 常量定义
 */
public class Constants {

    /**
     * Session中存储的用户信息key
     */
    public static final String SESSION_USER = "loginUser";

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 状态：启用
     */
    public static final int STATUS_ENABLE = 1;

    /**
     * 状态：禁用
     */
    public static final int STATUS_DISABLE = 0;

    /**
     * 逻辑删除：未删除
     */
    public static final int NOT_DELETED = 0;

    /**
     * 逻辑删除：已删除
     */
    public static final int DELETED = 1;
}
