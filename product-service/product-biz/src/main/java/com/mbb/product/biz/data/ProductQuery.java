package com.mbb.product.biz.data;

import lombok.Data;

@Data
public class ProductQuery {
    private String code;
    private String name;
    private String category;
    private String channel;
    private String approvalStatus;
}
