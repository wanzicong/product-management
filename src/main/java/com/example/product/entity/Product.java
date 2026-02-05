package com.example.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 */
@Data
@TableName("t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 销售价格
     */
    private BigDecimal price;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 库存预警值
     */
    private Integer stockWarning;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 重量(kg)
     */
    private BigDecimal weight;

    /**
     * 主图URL
     */
    private String mainImage;

    /**
     * 详情图URL(JSON数组)
     */
    private String images;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 状态: 0下架 1上架
     */
    private Integer status;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 逻辑删除: 0否 1是
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // ========== 非数据库字段 ==========

    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;
}
