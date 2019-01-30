package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.api.PosApi;
import com.mbb.stock.api.StoreApi;
import com.mbb.stock.common.dto.PosDetailData;
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
public class PosAdapter {


    @Autowired
    private StoreApi storeApi;

    @Autowired
    private PosApi posApi;

    public PageInfo<StoreInfoDto> getStores(String code, String name, Integer pageNum,
            Integer pageSize) {
        return storeApi.getStores(code, name, null, null, null, null, null, pageNum, pageSize);
    }


    public String getStoreNameById(Long id) {
        return storeApi.getStoreDetailById(id).getName();
    }

    public PosDetailData getPosDetail(Long id){
        if (id == null){
            return new PosDetailData();
        }
        return posApi.getPosDetailById(id);
    }
}
