package com.example.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.StockRecord;

/**
 * 库存 Service
 */
public interface StockService extends IService<StockRecord> {

    /**
     * 入库
     */
    void stockIn(Long productId, Integer quantity, String reason, String operator);

    /**
     * 出库
     */
    void stockOut(Long productId, Integer quantity, String reason, String operator);

    /**
     * 分页查询库存记录
     */
    IPage<StockRecord> pageRecord(Integer pageNum, Integer pageSize, Long productId, Integer type);
}
