package com.mbb.stock.biz.dto;


import lombok.Data;

@Data
public class StoreListQuery {
    private String name;
    private String code;
    private String type;
    private String owner;
    private String classification;
    private String people;
    private String status;
    private Integer pageNum;
    private Integer pageSize;
}
