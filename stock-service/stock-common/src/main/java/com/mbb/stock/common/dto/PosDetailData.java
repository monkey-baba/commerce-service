package com.mbb.stock.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class PosDetailData {
    private String code;
    private String name;
    private List<String> pcd;
    private String detailAddress;
    private String status;
    private String owner;
    private String contact;
    private String classify;
}
