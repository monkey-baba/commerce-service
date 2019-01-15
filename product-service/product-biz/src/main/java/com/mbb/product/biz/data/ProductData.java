package com.mbb.product.biz.data;

import com.mbb.product.biz.enumation.ApprovedStatus;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class ProductData {
    private String code;
    private String name;
    private Long channelId;
    private Long unitId;
    private ApprovedStatus approved;
    private List<String> images;
    private Map<Long,String> attribute;

}
