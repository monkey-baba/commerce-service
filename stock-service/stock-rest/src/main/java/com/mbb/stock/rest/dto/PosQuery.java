package com.mbb.stock.rest.dto;

import lombok.Data;

@Data
public class PosQuery {
    private String code;
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
