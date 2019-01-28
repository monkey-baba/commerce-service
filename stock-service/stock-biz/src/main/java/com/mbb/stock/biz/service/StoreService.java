package com.mbb.stock.biz.service;

import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;

import java.util.List;

public interface StoreService {
    List<PointOfServiceModel> getStores(PointOfServiceModel storeModel);
    List<StoreInfoDto> getAllStores();
    void addStore(StoreInfoDto stockInfoDtoList);
    void updateStore(PointOfServiceModel var1);
    PointOfServiceModel findById(Long var1);
}
