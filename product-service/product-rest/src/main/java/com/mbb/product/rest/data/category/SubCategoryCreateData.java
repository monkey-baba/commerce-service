package com.mbb.product.rest.data.category;

import lombok.Data;

@Data
public class SubCategoryCreateData {
    private String name;
    private Long parentId;
}
