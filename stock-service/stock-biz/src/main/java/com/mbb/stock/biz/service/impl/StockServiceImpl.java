package com.mbb.stock.biz.service.impl;


import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.StockMapper;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:38
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private static final Integer limit = 1;

    private static final Integer page = 1;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private IdService idService;

    @Override
    public List<StockModel> getStocks(StockModel stockModel) {
        Example example = mapQueryInfo(stockModel);
        List<StockModel> stockModels = stockMapper.selectByExample(example);
        log.info("stock size====" + stockModels.size());
        return stockModels;
    }

    @Override
    public void createStock(StockModel stockModel) {
        if (stockModel != null) {
            stockMapper.insert(stockModel);
        }
    }

    @Override
    public void deleteStock(String id) {
        stockMapper.deleteByPrimaryKey(id);
    }

    private Example mapQueryInfo(StockModel stockModel) {
        //商品编码
        Long skuId = stockModel.getSkuId();
        //商品名称
        //仓库编码
        Long warehouseId = stockModel.getWarehouseId();
        //仓库名称
        Example example = new Example(StockModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (skuId != null) {
            criteria.andEqualTo("code", skuId);
        }
        if (warehouseId != null) {
            criteria.andEqualTo("warehouseId", warehouseId);
        }
        return example;
    }
}
