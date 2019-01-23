package com.mbb.product.biz.data;

import lombok.Data;

@Data
public class CategoryQuery {
    private String code;
    private String name;
    private Long parentId;
    private Integer pageNum;
    private Integer pageSize;
}
