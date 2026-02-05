package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.common.BusinessException;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品 Service 实现
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public IPage<Product> pageProduct(Integer pageNum, Integer pageSize, String productName,
                                      String productCode, Long categoryId, Long brandId, Integer status) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectProductPage(page, productName, productCode, categoryId, brandId, status);
    }

    @Override
    public Product getProductDetail(Long id) {
        return baseMapper.selectProductDetail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(Product product) {
        // 检查商品编码是否重复
        Product exist = getOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getProductCode, product.getProductCode()));
        if (exist != null) {
            throw new BusinessException("商品编码已存在");
        }
        save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Product product) {
        // 检查商品编码是否重复（排除自身）
        Product exist = getOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getProductCode, product.getProductCode())
                .ne(Product::getId, product.getId()));
        if (exist != null) {
            throw new BusinessException("商品编码已存在");
        }
        updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long id) {
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        updateById(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateStatus(List<Long> ids, Integer status) {
        for (Long id : ids) {
            updateStatus(id, status);
        }
    }

    @Override
    public List<Product> listStockWarning() {
        return list(new LambdaQueryWrapper<Product>()
                .apply("stock <= stock_warning")
                .eq(Product::getStatus, 1)
                .orderByAsc(Product::getStock));
    }

    @Override
    public long countProduct() {
        return count();
    }

    @Override
    public long countOnShelf() {
        return count(new LambdaQueryWrapper<Product>()
                .eq(Product::getStatus, 1));
    }
}
