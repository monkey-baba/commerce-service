package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.customer.api.CustomerApi;
import com.mbb.customer.common.dto.CustomerData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hyx
 * @title OrderServiceAdapter
 * @description
 * @date 2019/1/22
 */
@Component
public class AccountAdapter {


    /**
     * 根据id获得用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public Map<String, String> getUserInfo(Long id) {
        return Collections.singletonMap("name", "管理员");
    }

}
