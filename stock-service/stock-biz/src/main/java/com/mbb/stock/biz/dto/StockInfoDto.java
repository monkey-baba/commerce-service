package com.mbb.stock.biz.dto;

import lombok.Data;

@Data
public class StockInfoDto {
    private String id;
    private String skuId;
    private String skuName;
    private String warehouseId;
    private String available;

}
