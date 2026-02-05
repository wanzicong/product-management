package com.example.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.common.Result;
import com.example.product.entity.Brand;
import com.example.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌控制器
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 品牌列表页面
     */
    @GetMapping("/list")
    public String list() {
        return "brand/list";
    }

    /**
     * 分页查询品牌数据
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<IPage<Brand>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String brandName) {
        IPage<Brand> page = brandService.pageBrand(pageNum, pageSize, brandName);
        return Result.success(page);
    }

    /**
     * 获取所有启用的品牌
     */
    @GetMapping("/listEnabled")
    @ResponseBody
    public Result<List<Brand>> listEnabled() {
        List<Brand> list = brandService.listEnabled();
        return Result.success(list);
    }

    /**
     * 新增品牌页面
     */
    @GetMapping("/add")
    public String add() {
        return "brand/form";
    }

    /**
     * 编辑品牌页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Brand brand = brandService.getById(id);
        model.addAttribute("brand", brand);
        return "brand/form";
    }

    /**
     * 保存品牌
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Void> save(@RequestBody Brand brand) {
        if (brand.getId() == null) {
            brandService.saveBrand(brand);
        } else {
            brandService.updateBrand(brand);
        }
        return Result.success("保存成功", null);
    }

    /**
     * 删除品牌
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Result<Void> delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return Result.success("删除成功", null);
    }
}
