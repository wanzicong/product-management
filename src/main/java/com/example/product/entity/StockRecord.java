package com.example.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存记录实体
 */
@Data
@TableName("t_stock_record")
public class StockRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 类型: 1入库 2出库
     */
    private Integer type;

    /**
     * 变动数量
     */
    private Integer quantity;

    /**
     * 变动前库存
     */
    private Integer beforeStock;

    /**
     * 变动后库存
     */
    private Integer afterStock;

    /**
     * 变动原因
     */
    private String reason;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // ========== 非数据库字段 ==========

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String productName;

    /**
     * 商品编码
     */
    @TableField(exist = false)
    private String productCode;
}
