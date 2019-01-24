package com.mbb.product.biz.service;


import com.mbb.product.biz.model.PriceModel;

import java.util.List;

public interface PriceService {
    List<PriceModel> getPrices(PriceModel priceModel);

    void deletePrice(Long id);
    PriceModel findPriceById(Long id);
    void updateprice(PriceModel price) throws Exception;
    PriceModel createPrice(PriceModel price);
}
