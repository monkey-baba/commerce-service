package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.api.StoreApi;
import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hyx
 * @title OrderServiceAdapter
 * @description
 * @date 2019/1/22
 */
@Component
public class StoreAdapter {


    @Autowired
    private StoreApi storeApi;

    public PageInfo<StoreInfoDto> getCustomers(String code, String name, Integer pageNum,
            Integer pageSize) {
        return storeApi.getStores(code, name, null, null, null, null, null, pageNum, pageSize);
    }


    public String getNameById(Long id) {
        return "大华仓";
    }
}
