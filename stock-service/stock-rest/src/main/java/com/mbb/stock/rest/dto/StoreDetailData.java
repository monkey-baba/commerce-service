package com.mbb.stock.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoreDetailData {
    private String code;
    private String name;
    private Long address;
    private List<String> paddress;
    private String detailaddress;
    private String pstatus;
    private Long status;
    private String owner;
    private String contact;
    private String ClassifyId;
}
