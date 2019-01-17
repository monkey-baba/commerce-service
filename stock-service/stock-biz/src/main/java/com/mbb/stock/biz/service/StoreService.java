package com.mbb.stock.biz.service;

import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.dto.StoreListQuery;
import java.util.List;

public interface StoreService {
    List<StoreInfoDto> getStores(StoreListQuery storeListQuery);
    List<StoreInfoDto> getAllStores();
    void addStore(List<StoreInfoDto> stockInfoDtoList);
}
