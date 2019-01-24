package com.mbb.product.rest.data.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryData {
    private Long id;
    private String code;
    private String name;
    private List<CategoryData> children;
}
