package com.mbb.order.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-21 11:28
 */
@Component
public class DictValueAdapter {
    @Autowired
    private DictValueApi dictValueApi;

    public List<DictValueData> getDictValues(String type) {
        // 远程调⽤用
        return dictValueApi.getDictValues(type);
    }
}
