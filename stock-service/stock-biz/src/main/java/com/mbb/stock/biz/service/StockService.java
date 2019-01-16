package com.mbb.stock.biz.service;

import com.mbb.stock.biz.model.StockModel;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface StockService {

    /**
     * 库存查询
     *
     * @param stockModel
     * @return
     */
    List<StockModel> getStocks(StockModel stockModel);

    /**
     * 新增库存
     *
     * @param stockModel
     */
    void createStock(StockModel stockModel);

    /**
     * 删除库存
     *
     * @param id
     */
    void deleteStock(String id);

}
