package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class WarehouseInfoResp {
    private Long id;
    private String code;
    private String name;
    private String active;
    private Long posId;
    private String posAddress;

}
