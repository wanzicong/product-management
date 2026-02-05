package com.example.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类实体
 */
@Data
@TableName("t_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 父分类ID, 0为顶级
     */
    private Long parentId;

    /**
     * 层级: 1/2/3
     */
    private Integer level;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 状态: 0禁用 1启用
     */
    private Integer status;

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
}
