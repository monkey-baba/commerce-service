package com.mbb.product.biz.data;

import lombok.Data;

@Data
public class ProductQuery {
    private String code;
    private String name;
    private Long channelId;
    private Long approvedId;
    private Integer pageNum;
    private Integer pageSize;
}
