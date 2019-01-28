package com.mbb.product.rest.data.category;

import lombok.Data;

@Data
public class CategoryQuery {
    private String code;
    private String name;
    private Long parentId;
    private Integer pageNum;
    private Integer pageSize;
}
