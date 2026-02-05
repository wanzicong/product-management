package com.example.product.controller;

import com.example.product.common.Result;
import com.example.product.dto.response.CategoryTreeVO;
import com.example.product.entity.Category;
import com.example.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表页面
     */
    @GetMapping("/list")
    public String list() {
        return "category/list";
    }

    /**
     * 获取分类树形数据
     */
    @GetMapping("/tree")
    @ResponseBody
    public Result<List<CategoryTreeVO>> tree() {
        List<CategoryTreeVO> tree = categoryService.getCategoryTree();
        return Result.success(tree);
    }

    /**
     * 获取所有启用的分类
     */
    @GetMapping("/listEnabled")
    @ResponseBody
    public Result<List<Category>> listEnabled() {
        List<Category> list = categoryService.listEnabled();
        return Result.success(list);
    }

    /**
     * 新增分类页面
     */
    @GetMapping("/add")
    public String add(@RequestParam(required = false) Long parentId, Model model) {
        model.addAttribute("parentId", parentId != null ? parentId : 0);
        if (parentId != null && parentId > 0) {
            Category parent = categoryService.getById(parentId);
            model.addAttribute("parentName", parent != null ? parent.getCategoryName() : "");
        } else {
            model.addAttribute("parentName", "顶级分类");
        }
        return "category/form";
    }

    /**
     * 编辑分类页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Category category = categoryService.getById(id);
        model.addAttribute("category", category);
        if (category.getParentId() != null && category.getParentId() > 0) {
            Category parent = categoryService.getById(category.getParentId());
            model.addAttribute("parentName", parent != null ? parent.getCategoryName() : "");
        } else {
            model.addAttribute("parentName", "顶级分类");
        }
        return "category/form";
    }

    /**
     * 保存分类
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Void> save(@RequestBody Category category) {
        if (category.getId() == null) {
            categoryService.saveCategory(category);
        } else {
            categoryService.updateCategory(category);
        }
        return Result.success("保存成功", null);
    }

    /**
     * 删除分类
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success("删除成功", null);
    }
}
