package com.mbb.product.rest.data.sku;

import com.mbb.product.common.dto.SkuMetaData;
import lombok.Data;

import java.util.List;

@Data
public class SkuSaveData {
    private String skuId;
    private String skuName;
    private List<SkuMetaData> meta;

}
