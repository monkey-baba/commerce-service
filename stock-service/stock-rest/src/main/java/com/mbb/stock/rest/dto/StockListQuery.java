package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class StockListQuery {

    private Long skuId;
    private String skuName;
    private Long warehouseId;
    private String warehouseName;
    private Integer pageNum;
    private Integer pageSize;
}
