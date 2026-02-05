package com.example.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品 Mapper
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询商品（带分类和品牌名称）
     */
    IPage<Product> selectProductPage(IPage<Product> page,
                                     @Param("productName") String productName,
                                     @Param("productCode") String productCode,
                                     @Param("categoryId") Long categoryId,
                                     @Param("brandId") Long brandId,
                                     @Param("status") Integer status);

    /**
     * 查询商品详情（带分类和品牌名称）
     */
    Product selectProductDetail(@Param("id") Long id);
}
