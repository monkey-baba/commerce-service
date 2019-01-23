package com.mbb.product.biz.data;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryUpdateData {
    private Long id;
    private String code;
    private String name;
    private Long parentId;
}
