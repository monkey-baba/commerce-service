package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.SellerRemarkMapper;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.model.SellerRemarkModel;
import com.mbb.order.biz.service.RemarkService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

@Service
@Slf4j
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private SellerRemarkMapper sellerRemarkMapper;

    @Override
    public void insert(SellerRemarkModel sellerRemark) {
        sellerRemarkMapper.insert(sellerRemark);
    }

    @Override
    public List<SellerRemarkModel> getRemarksByOrderId(Long id) {
        Builder builder = Example.builder(SellerRemarkModel.class);
        builder.where(Sqls.custom().andEqualTo("orderId",id));
        return sellerRemarkMapper.selectByExample(builder.build());
    }
}
