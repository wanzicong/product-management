package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.common.BusinessException;
import com.example.product.dto.response.CategoryTreeVO;
import com.example.product.entity.Category;
import com.example.product.mapper.CategoryMapper;
import com.example.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类 Service 实现
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<CategoryTreeVO> getCategoryTree() {
        List<Category> allCategories = list(new LambdaQueryWrapper<Category>()
                .orderByAsc(Category::getSortOrder)
                .orderByAsc(Category::getId));
        return buildTree(allCategories, 0L);
    }

    /**
     * 递归构建树形结构
     */
    private List<CategoryTreeVO> buildTree(List<Category> categories, Long parentId) {
        List<CategoryTreeVO> tree = new ArrayList<>();
        for (Category category : categories) {
            if (parentId.equals(category.getParentId())) {
                CategoryTreeVO vo = new CategoryTreeVO();
                BeanUtils.copyProperties(category, vo);
                vo.setChildren(buildTree(categories, category.getId()));
                tree.add(vo);
            }
        }
        return tree;
    }

    @Override
    public List<Category> listEnabled() {
        return list(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, 1)
                .orderByAsc(Category::getLevel)
                .orderByAsc(Category::getSortOrder));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        // 设置层级
        if (category.getParentId() == null || category.getParentId() == 0) {
            category.setParentId(0L);
            category.setLevel(1);
        } else {
            Category parent = getById(category.getParentId());
            if (parent == null) {
                throw new BusinessException("父分类不存在");
            }
            if (parent.getLevel() >= 3) {
                throw new BusinessException("分类层级不能超过3级");
            }
            category.setLevel(parent.getLevel() + 1);
        }
        save(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Category category) {
        Category old = getById(category.getId());
        if (old == null) {
            throw new BusinessException("分类不存在");
        }
        // 如果修改了父分类，需要重新计算层级
        if (!old.getParentId().equals(category.getParentId())) {
            if (category.getParentId() == null || category.getParentId() == 0) {
                category.setParentId(0L);
                category.setLevel(1);
            } else {
                Category parent = getById(category.getParentId());
                if (parent == null) {
                    throw new BusinessException("父分类不存在");
                }
                if (parent.getLevel() >= 3) {
                    throw new BusinessException("分类层级不能超过3级");
                }
                category.setLevel(parent.getLevel() + 1);
            }
        }
        updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 检查是否有子分类
        long childCount = count(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, id));
        if (childCount > 0) {
            throw new BusinessException("该分类下有子分类，无法删除");
        }
        removeById(id);
    }

    @Override
    public long countCategory() {
        return count();
    }
}
