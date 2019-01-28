package com.mbb.product.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceAdapter {
    @Autowired
    private DictValueApi dictValueApi;

    public List<DictValueData> getChannel(){
        // 远程调⽤用
        return dictValueApi.getChannel();
    }

    public List<DictValueData> getProductAttr(){
        // 远程调⽤用
        return dictValueApi.getProductAttr();
    }

    public List<DictValueData> getSkuSpec(){
        // 远程调⽤用
        return dictValueApi.getSkuSpec();
    }

    public List<DictValueData> getUnit(){
        // 远程调⽤用
        return dictValueApi.getUnit();
    }

    public List<DictValueData> getApprovedStatus(){
        // 远程调⽤用
        return dictValueApi.getApprovedStatus();
    }

    public List<DictValueData> getPriceType(){
        // 远程调⽤用
        return dictValueApi.getPriceType();
    }

}
