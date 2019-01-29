package com.mbb.order.biz.service;

import com.mbb.order.biz.model.OrderEntryModel;
import java.util.List;

public interface OrderEntryService {

    void insertEntries(List<OrderEntryModel> entries);

    List<OrderEntryModel> getEntriesByOrderId(Long id);
}
