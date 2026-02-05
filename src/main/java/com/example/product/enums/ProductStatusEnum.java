package com.example.product.enums;

import lombok.Getter;

/**
 * 商品状态枚举
 */
@Getter
public enum ProductStatusEnum {

    OFF_SHELF(0, "下架"),
    ON_SHELF(1, "上架");

    private final Integer code;
    private final String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (ProductStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return "";
    }
}
