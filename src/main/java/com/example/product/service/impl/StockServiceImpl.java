package com.example.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.product.common.BusinessException;
import com.example.product.entity.Product;
import com.example.product.entity.StockRecord;
import com.example.product.enums.StockTypeEnum;
import com.example.product.mapper.StockRecordMapper;
import com.example.product.service.ProductService;
import com.example.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存 Service 实现
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockRecordMapper, StockRecord> implements StockService {

    @Autowired
    private ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stockIn(Long productId, Integer quantity, String reason, String operator) {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (quantity <= 0) {
            throw new BusinessException("入库数量必须大于0");
        }

        int beforeStock = product.getStock();
        int afterStock = beforeStock + quantity;

        // 更新商品库存
        product.setStock(afterStock);
        productService.updateById(product);

        // 记录库存变动
        StockRecord record = new StockRecord();
        record.setProductId(productId);
        record.setType(StockTypeEnum.IN.getCode());
        record.setQuantity(quantity);
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setReason(reason);
        record.setOperator(operator);
        save(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stockOut(Long productId, Integer quantity, String reason, String operator) {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (quantity <= 0) {
            throw new BusinessException("出库数量必须大于0");
        }
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足，当前库存：" + product.getStock());
        }

        int beforeStock = product.getStock();
        int afterStock = beforeStock - quantity;

        // 更新商品库存
        product.setStock(afterStock);
        productService.updateById(product);

        // 记录库存变动
        StockRecord record = new StockRecord();
        record.setProductId(productId);
        record.setType(StockTypeEnum.OUT.getCode());
        record.setQuantity(quantity);
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setReason(reason);
        record.setOperator(operator);
        save(record);
    }

    @Override
    public IPage<StockRecord> pageRecord(Integer pageNum, Integer pageSize, Long productId, Integer type) {
        Page<StockRecord> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectRecordPage(page, productId, type);
    }
}
