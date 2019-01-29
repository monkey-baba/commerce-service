package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.SellerRemarkMapper;
import com.mbb.order.biz.model.SellerRemarkModel;
import com.mbb.order.biz.service.RemarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private SellerRemarkMapper sellerRemarkMapper;

    @Override
    public void insert(SellerRemarkModel sellerRemark) {
        sellerRemarkMapper.insert(sellerRemark);
    }
}
