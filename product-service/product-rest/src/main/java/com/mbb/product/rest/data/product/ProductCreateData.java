package com.mbb.product.rest.data.product;

import lombok.Data;

@Data
public class ProductCreateData {
    private String code;
    private String name;
    private Long channelId;
    private Long approvedId;
}
