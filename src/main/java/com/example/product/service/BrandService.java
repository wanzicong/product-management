package com.example.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.Brand;

import java.util.List;

/**
 * 品牌 Service
 */
public interface BrandService extends IService<Brand> {

    /**
     * 分页查询品牌
     */
    IPage<Brand> pageBrand(Integer pageNum, Integer pageSize, String brandName);

    /**
     * 获取所有启用的品牌列表
     */
    List<Brand> listEnabled();

    /**
     * 保存品牌
     */
    void saveBrand(Brand brand);

    /**
     * 更新品牌
     */
    void updateBrand(Brand brand);

    /**
     * 删除品牌
     */
    void deleteBrand(Long id);

    /**
     * 统计品牌数量
     */
    long countBrand();
}
