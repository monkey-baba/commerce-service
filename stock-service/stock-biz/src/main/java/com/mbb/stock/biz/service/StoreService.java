package com.mbb.stock.biz.service;

import com.mbb.stock.biz.model.PointOfServiceModel;

import com.mbb.stock.biz.model.StorePosModel;
import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.List;
import java.util.Map;

public interface StoreService {
    List<PointOfServiceModel> getStores(PointOfServiceModel storeModel);
    List<StoreInfoDto> getAllStores();
    void addStore(StoreInfoDto stockInfoDtoList);
    void updateStore(PointOfServiceModel var1);
    PointOfServiceModel findById(Long var1);
    PointOfServiceModel findPosById(Long id);
    List<PointOfServiceModel> getPos(PointOfServiceModel storeModel);
    void addStorePos(StorePosModel storePosModel);
    List<StorePosModel> getStorePos(Map<String, Object> parameters);
    void deleteStorePos(Long id);
}
