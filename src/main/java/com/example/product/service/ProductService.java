package com.example.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.Product;

import java.util.List;

/**
 * 商品 Service
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询商品
     */
    IPage<Product> pageProduct(Integer pageNum, Integer pageSize, String productName,
                               String productCode, Long categoryId, Long brandId, Integer status);

    /**
     * 获取商品详情
     */
    Product getProductDetail(Long id);

    /**
     * 保存商品
     */
    void saveProduct(Product product);

    /**
     * 更新商品
     */
    void updateProduct(Product product);

    /**
     * 删除商品
     */
    void deleteProduct(Long id);

    /**
     * 批量删除商品
     */
    void batchDelete(List<Long> ids);

    /**
     * 上架/下架商品
     */
    void updateStatus(Long id, Integer status);

    /**
     * 批量上架/下架
     */
    void batchUpdateStatus(List<Long> ids, Integer status);

    /**
     * 查询库存预警商品
     */
    List<Product> listStockWarning();

    /**
     * 统计商品数量
     */
    long countProduct();

    /**
     * 统计上架商品数量
     */
    long countOnShelf();
}
