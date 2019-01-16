package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class WarehouseListQuery {

    private String code;
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
