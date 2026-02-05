package com.example.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.common.Result;
import com.example.product.entity.Brand;
import com.example.product.entity.Category;
import com.example.product.entity.Product;
import com.example.product.service.BrandService;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    /**
     * 商品列表页面
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<Category> categories = categoryService.listEnabled();
        List<Brand> brands = brandService.listEnabled();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "product/list";
    }

    /**
     * 分页查询商品数据
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<IPage<Product>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Integer status) {
        IPage<Product> page = productService.pageProduct(pageNum, pageSize, productName, productCode, categoryId, brandId, status);
        return Result.success(page);
    }

    /**
     * 新增商品页面
     */
    @GetMapping("/add")
    public String add(Model model) {
        List<Category> categories = categoryService.listEnabled();
        List<Brand> brands = brandService.listEnabled();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "product/add";
    }

    /**
     * 编辑商品页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product product = productService.getProductDetail(id);
        List<Category> categories = categoryService.listEnabled();
        List<Brand> brands = brandService.listEnabled();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "product/edit";
    }

    /**
     * 商品详情页面
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = productService.getProductDetail(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    /**
     * 保存商品
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Void> save(@RequestBody Product product) {
        productService.saveProduct(product);
        return Result.success("保存成功", null);
    }

    /**
     * 更新商品
     */
    @PostMapping("/update")
    @ResponseBody
    public Result<Void> update(@RequestBody Product product) {
        productService.updateProduct(product);
        return Result.success("更新成功", null);
    }

    /**
     * 删除商品
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success("删除成功", null);
    }

    /**
     * 批量删除商品
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        productService.batchDelete(ids);
        return Result.success("批量删除成功", null);
    }

    /**
     * 上架/下架商品
     */
    @PostMapping("/updateStatus")
    @ResponseBody
    public Result<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
        productService.updateStatus(id, status);
        return Result.success(status == 1 ? "上架成功" : "下架成功", null);
    }

    /**
     * 批量上架/下架
     */
    @PostMapping("/batchUpdateStatus")
    @ResponseBody
    public Result<Void> batchUpdateStatus(@RequestBody List<Long> ids, @RequestParam Integer status) {
        productService.batchUpdateStatus(ids, status);
        return Result.success(status == 1 ? "批量上架成功" : "批量下架成功", null);
    }
}
