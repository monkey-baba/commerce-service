package com.mbb.product.rest.data.sku;

import lombok.Data;

import java.util.List;
@Data
public class SkuDetailData {
    private String name;
    private List<SkuMeataDetailData> value;
}
