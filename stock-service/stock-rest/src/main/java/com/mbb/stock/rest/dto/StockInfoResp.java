package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class StockInfoResp {
    private Long id;
    private Long skuId;
    private String skuName;
    private Long warehouseId;
    private Long available;

}
