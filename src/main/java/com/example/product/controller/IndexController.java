package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.entity.User;
import com.example.product.service.BrandService;
import com.example.product.service.CategoryService;
import com.example.product.service.ProductService;
import com.example.product.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    /**
     * 首页/仪表盘
     */
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        User loginUser = SessionUtil.getLoginUser();
        model.addAttribute("user", loginUser);

        // 统计数据
        long productCount = productService.countProduct();
        long onShelfCount = productService.countOnShelf();
        long categoryCount = categoryService.countCategory();
        long brandCount = brandService.countBrand();

        model.addAttribute("productCount", productCount);
        model.addAttribute("onShelfCount", onShelfCount);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("brandCount", brandCount);

        // 库存预警商品
        List<Product> warningProducts = productService.listStockWarning();
        model.addAttribute("warningProducts", warningProducts);
        model.addAttribute("warningCount", warningProducts.size());

        return "index";
    }
}
