package com.mbb.product.biz.data;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class ProductData {
    private String code;
    private String name;
    private Long channelId;
    private Long id;
    private Long unitId;
    private Long approvedId;
    private List<String> images;
    private Map<Long,String> attribute;

}
