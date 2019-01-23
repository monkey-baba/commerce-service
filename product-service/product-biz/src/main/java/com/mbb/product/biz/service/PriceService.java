package com.mbb.product.biz.service;

import com.github.pagehelper.PageInfo;
import com.mbb.product.biz.data.PriceData;
import com.mbb.product.biz.data.PriceQuery;
import com.mbb.product.biz.model.PriceModel;

public interface PriceService {
    PageInfo<PriceData> getPrices(PriceQuery priceQuery);
    void deletePrice(Long id);
    PriceModel findPriceById(Long id);
    void updateprice(PriceModel price) throws Exception;
    PriceModel createPrice(PriceModel price);
}
