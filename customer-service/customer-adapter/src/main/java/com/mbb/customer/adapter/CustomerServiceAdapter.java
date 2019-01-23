package com.mbb.customer.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-22 16:23
 */
@Component
public class CustomerServiceAdapter {
    @Autowired
    private DictValueApi dictValueApi;

    public List<DictValueData> getCustomerStatus() {
        // 远程调⽤用
        return dictValueApi.getCustomerStatus();
    }

    public DictValueData getDictValue(Long id) {
        return dictValueApi.getDictValue(id);
    }
}
