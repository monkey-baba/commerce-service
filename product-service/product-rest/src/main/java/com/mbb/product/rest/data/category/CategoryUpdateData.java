package com.mbb.product.rest.data.category;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryUpdateData {
    private Long id;
    private String code;
    private String name;
    private Long parentId;
}
