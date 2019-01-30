package com.mbb.order.biz.service;

import com.mbb.order.biz.model.ConsignmentEntryModel;

import java.util.List;

/**
 * @author hyx
 * @title ConsignmentEntryService
 * @description
 * @date 2019/1/30
 */
public interface ConsignmentEntryService {

    /**
     * 根据配货单id查询配货单行
     *
     * @param id
     * @return
     */
    List<ConsignmentEntryModel> getConsignmentEntriesByConsignmentId(Long id);
}
