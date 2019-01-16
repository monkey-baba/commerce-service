package com.mbb.stock.biz.dto;

import lombok.Data;

@Data
public class WarehouseInfoDto {
    private String id;
    private String code;
    private String name;
    private String active;
    private String posId;
    private String posAddress;

}
