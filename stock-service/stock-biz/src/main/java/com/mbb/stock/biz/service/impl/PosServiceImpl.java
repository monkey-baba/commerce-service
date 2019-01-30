package com.mbb.stock.biz.service.impl;

import com.mbb.stock.biz.dao.PosMapper;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.service.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosServiceImpl implements PosService {

    @Autowired
    private PosMapper posMapper;
    @Override
    public PointOfServiceModel findById(Long id) {
        return posMapper.selectByPrimaryKey(id);
    }
}
