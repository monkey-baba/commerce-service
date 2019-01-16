package com.mbb.stock.biz.service;

import com.mbb.stock.biz.dto.StockInfoDto;
import com.mbb.stock.biz.dto.StockQueryDto;

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
     * @param stockQueryDto
     * @return
     */
    List<StockInfoDto> getStocks(StockQueryDto stockQueryDto);

    /**
     * 新增库存
     *
     * @param stockInfoDtoList
     */
    void addStock(List<StockInfoDto> stockInfoDtoList);

    /**
     * 删除库存
     *
     * @param id
     */
    void deleteStock(String id);

    List<StockInfoDto> getAllStocks();

}
