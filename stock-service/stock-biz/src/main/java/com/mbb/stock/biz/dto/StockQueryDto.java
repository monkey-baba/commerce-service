package com.mbb.stock.biz.dto;

import lombok.Data;

@Data
public class StockQueryDto {

    private String skuId;
    private String skuName;
    private String warehouseId;
    private String warehouseName;
    private String offset;

}
