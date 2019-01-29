package com.mbb.order.biz.service;

import com.mbb.order.biz.model.SellerRemarkModel;
import java.util.List;

public interface RemarkService {

    void insert(SellerRemarkModel sellerRemark);

    List<SellerRemarkModel> getRemarksByOrderId(Long id);
}
