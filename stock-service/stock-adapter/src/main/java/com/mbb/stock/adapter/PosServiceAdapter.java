package com.mbb.stock.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 此处写此服务调用别的服务的适配器Adapter
 */
@Component
public class PosServiceAdapter {


    @Autowired
    private DictValueApi dictValueApi;

    public List<DictValueData> getPosClassify() {
        //远程调用
        return dictValueApi.getPosClassify();
    }

    public List<DictValueData> getPosStatus() {
        //远程调用
        return dictValueApi.getPosStatus();
    }

    public List<DictValueData> getDcClassify(){
        //远程调用
        return dictValueApi.getDcClassify();
    }

    public DictValueData getDictValue(Long id){
        //远程调用
        return dictValueApi.getDictValue(id);
    }
    public List<DictValueData> getBaseStore(){
        //远程调用
        return dictValueApi.getBaseStore();
    }

    public List<DictValueData> getPosType() {
        //远程调用
        return dictValueApi.getPosType();
    }

    public String getDictValueName(Long id) {
        if (id == null){
            return Strings.EMPTY;
        }
        //远程调用
        DictValueData dictValue = dictValueApi.getDictValue(id);
        if (dictValue != null) {
            return dictValue.getName();
        }
        return String.valueOf(id);
    }
}