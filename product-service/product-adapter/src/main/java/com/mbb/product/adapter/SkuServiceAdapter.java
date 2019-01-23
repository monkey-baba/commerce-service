package com.mbb.product.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuServiceAdapter {
    @Autowired    private DictValueApi dictValueApi;
    public List<DictValueData> getPosType(){
        // 远程调⽤用
    return dictValueApi.getPosType();
    }


    }
