package com.mbb.product.biz.service;


import com.mbb.product.biz.model.PriceRowModel;

import java.util.List;


public interface PriceRowService {
    List<PriceRowModel> getPriceRows(PriceRowModel priceRowModel);
    void deletePriceRow(Long id);
    PriceRowModel findPriceRowById(Long id);
    void updatePriceRow(PriceRowModel priceRow) throws Exception;
    PriceRowModel createPriceRow(PriceRowModel priceRow);
}
