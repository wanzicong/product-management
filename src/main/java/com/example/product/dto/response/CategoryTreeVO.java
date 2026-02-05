package com.example.product.dto.response;

import lombok.Data;

import java.util.List;

/**
 * 分类树形结构 VO
 */
@Data
public class CategoryTreeVO {

    private Long id;
    private String categoryName;
    private Long parentId;
    private Integer level;
    private Integer sortOrder;
    private Integer status;
    private List<CategoryTreeVO> children;
}
