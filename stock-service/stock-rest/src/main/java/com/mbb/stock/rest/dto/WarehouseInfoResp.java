package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class WarehouseInfoResp {
    private Long id;
    private String code;
    private String name;
    private Boolean enabled;
    private Long posId;
    private String posName;
    private String posAddress;

}
