package com.mbb.product.rest.data.product;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductBasicData {
    private String code;
    private Long id;
    private String name;
    private Long channelId;
    private Long categoryId;
    private Long approvedId;
    private Long unitId;
    private List<String> images;
    private Map<Long,String> attribute;
}
