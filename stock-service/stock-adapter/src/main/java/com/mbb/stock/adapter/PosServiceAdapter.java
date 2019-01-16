package com.mbb.stock.adapter;

import com.mbb.basic.api.PosServiceApi;
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
    private PosServiceApi posServiceApi;

    public List<DictValueData> getPosType(){
        //远程调用
        return posServiceApi.getPosType();
    }

}