package com.example.product.enums;

import lombok.Getter;

/**
 * 库存变动类型枚举
 */
@Getter
public enum StockTypeEnum {

    IN(1, "入库"),
    OUT(2, "出库");

    private final Integer code;
    private final String desc;

    StockTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (StockTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return "";
    }
}
