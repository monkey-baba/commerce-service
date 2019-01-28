package com.mbb.product.rest.data.product;

import lombok.Data;

@Data
public class ProductUpdateData {
    private String code;
    private Long id;
    private String name;
    private Long channelId;
    private Long approvedId;
}
