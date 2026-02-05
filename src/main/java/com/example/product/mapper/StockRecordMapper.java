package com.example.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.product.entity.StockRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存记录 Mapper
 */
@Mapper
public interface StockRecordMapper extends BaseMapper<StockRecord> {

    /**
     * 分页查询库存记录（带商品信息）
     */
    IPage<StockRecord> selectRecordPage(IPage<StockRecord> page,
                                        @Param("productId") Long productId,
                                        @Param("type") Integer type);
}
