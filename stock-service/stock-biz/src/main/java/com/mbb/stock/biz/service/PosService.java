package com.mbb.stock.biz.service;

import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.List;

public interface PosService {

    PointOfServiceModel findById(Long id);

}
