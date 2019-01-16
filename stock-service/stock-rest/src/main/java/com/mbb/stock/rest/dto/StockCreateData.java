package com.mbb.stock.rest.dto;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-16 15:59
 */
@Data
public class StockCreateData {
    private Long skuId;
    private String skuName;
    private Long warehouseId;
    private Long available;
}
