package com.mbb.product.biz.service;

import com.mbb.product.biz.model.SkuMetaModel;

import java.util.List;

public interface SkuMetaService {

    List<SkuMetaModel> getSkuMetaModels(SkuMetaModel productModel);

    SkuMetaModel getSkuMetaById(Long id);
}
