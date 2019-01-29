package com.mbb.stock.common.dto;
import java.util.List;
import lombok.Data;

@Data
public class StoreInfoDto {
    private Long id;
    private String name;
    private Long address;
    private List<String> paddress;
    private String detailaddress;
    private String pstatus;
    private Long status;
    private String contact;
    private String code;
    private String owner;
    private Long classification;
}
