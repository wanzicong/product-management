package com.example.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.common.Result;
import com.example.product.entity.Product;
import com.example.product.entity.StockRecord;
import com.example.product.entity.User;
import com.example.product.service.ProductService;
import com.example.product.service.StockService;
import com.example.product.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存控制器
 */
@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    /**
     * 库存列表页面
     */
    @GetMapping("/list")
    public String list() {
        return "stock/list";
    }

    /**
     * 库存预警页面
     */
    @GetMapping("/warning")
    public String warning(Model model) {
        List<Product> warningProducts = productService.listStockWarning();
        model.addAttribute("products", warningProducts);
        return "stock/warning";
    }

    /**
     * 库存调整页面
     */
    @GetMapping("/adjust/{id}")
    public String adjust(@PathVariable Long id, Model model) {
        Product product = productService.getProductDetail(id);
        model.addAttribute("product", product);
        return "stock/adjust";
    }

    /**
     * 库存记录页面
     */
    @GetMapping("/record")
    public String record() {
        return "stock/record";
    }

    /**
     * 分页查询库存记录
     */
    @GetMapping("/recordPage")
    @ResponseBody
    public Result<IPage<StockRecord>> recordPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Integer type) {
        IPage<StockRecord> page = stockService.pageRecord(pageNum, pageSize, productId, type);
        return Result.success(page);
    }

    /**
     * 入库
     */
    @PostMapping("/in")
    @ResponseBody
    public Result<Void> stockIn(@RequestParam Long productId,
                                @RequestParam Integer quantity,
                                @RequestParam(required = false) String reason) {
        User loginUser = SessionUtil.getLoginUser();
        String operator = loginUser != null ? loginUser.getUsername() : "system";
        stockService.stockIn(productId, quantity, reason, operator);
        return Result.success("入库成功", null);
    }

    /**
     * 出库
     */
    @PostMapping("/out")
    @ResponseBody
    public Result<Void> stockOut(@RequestParam Long productId,
                                 @RequestParam Integer quantity,
                                 @RequestParam(required = false) String reason) {
        User loginUser = SessionUtil.getLoginUser();
        String operator = loginUser != null ? loginUser.getUsername() : "system";
        stockService.stockOut(productId, quantity, reason, operator);
        return Result.success("出库成功", null);
    }
}
