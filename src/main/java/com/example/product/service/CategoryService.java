package com.example.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.dto.response.CategoryTreeVO;
import com.example.product.entity.Category;

import java.util.List;

/**
 * 分类 Service
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类树形结构
     */
    List<CategoryTreeVO> getCategoryTree();

    /**
     * 获取所有启用的分类列表
     */
    List<Category> listEnabled();

    /**
     * 保存分类
     */
    void saveCategory(Category category);

    /**
     * 更新分类
     */
    void updateCategory(Category category);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);

    /**
     * 统计分类数量
     */
    long countCategory();
}
