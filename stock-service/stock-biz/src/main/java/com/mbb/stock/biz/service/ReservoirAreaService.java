package com.mbb.stock.biz.service;

import com.mbb.stock.biz.model.PointOfServiceModel;

import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.List;

public interface ReservoirAreaService {
    List<PointOfServiceModel> getReservoirAreas(PointOfServiceModel reservoirAreaModel);
    void addReservoirArea(StoreInfoDto stockInfoDtoList);
    void updateReservoirArea(PointOfServiceModel var1);
    PointOfServiceModel findById(Long var1);
}
