package com.mbb.product.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class SkuData {
    private Long id;
    private String code;
    private String name;
    private List<SkuMetaData> meta;
}
