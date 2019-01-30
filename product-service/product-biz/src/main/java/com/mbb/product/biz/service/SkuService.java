package com.mbb.product.biz.service;

import com.mbb.product.biz.model.SkuMetaModel;
import com.mbb.product.biz.model.SkuModel;
import java.util.List;

public interface SkuService {

    List<SkuModel> getSkus(SkuModel sku);

    void deleteSku(Long id);

    SkuModel findSkuById(Long id);

    SkuModel findSkuByCode(String skuId);

    void updateSku(SkuModel sku) throws Exception;

    SkuModel createSku(SkuModel sku);

}
