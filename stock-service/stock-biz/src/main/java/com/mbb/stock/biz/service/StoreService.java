package com.mbb.stock.biz.service;

import com.mbb.stock.biz.model.PointOfServiceModel;

import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.List;

public interface StoreService {
    List<PointOfServiceModel> getStores(PointOfServiceModel storeModel);
    List<StoreInfoDto> getAllStores();
    void addStore(StoreInfoDto stockInfoDtoList);
    void updateStore(PointOfServiceModel var1);
    PointOfServiceModel findById(Long var1);
    PointOfServiceModel findPosById(Long id);
    List<PointOfServiceModel> getPos(PointOfServiceModel storeModel);
}
