package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class StorePosListData {
    private Long id;
    private String storeName;
    private Long storeCode;
    private String posName;
    private String posCode;
    private Long posType;
    private String posTypeName;
    private Integer pageNum;
    private Integer pageSize;
}
