package com.mbb.stock.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 此处写此服务调用别的服务的适配器Adapter
 */
@Component
public class PosServiceAdapter {


    @Autowired
    private DictValueApi dictValueApi;

    public List<DictValueData> getPosClassify(){
        //远程调用
        return dictValueApi.getPosClassify();
    }

    public List<DictValueData> getPosStatus(){
        //远程调用
        return dictValueApi.getPosStatus();
    }
}