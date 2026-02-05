package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.entity.Brand;
import com.example.product.mapper.BrandMapper;
import com.example.product.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 品牌 Service 实现
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Override
    public IPage<Brand> pageBrand(Integer pageNum, Integer pageSize, String brandName) {
        Page<Brand> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(brandName)) {
            wrapper.like(Brand::getBrandName, brandName);
        }
        wrapper.orderByAsc(Brand::getSortOrder).orderByDesc(Brand::getId);
        return page(page, wrapper);
    }

    @Override
    public List<Brand> listEnabled() {
        return list(new LambdaQueryWrapper<Brand>()
                .eq(Brand::getStatus, 1)
                .orderByAsc(Brand::getSortOrder));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand) {
        save(brand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand) {
        updateById(brand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        removeById(id);
    }

    @Override
    public long countBrand() {
        return count();
    }
}
