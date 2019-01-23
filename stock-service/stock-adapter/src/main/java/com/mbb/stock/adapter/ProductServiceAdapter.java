package com.mbb.stock.adapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-21 23:27
 */
@Component
public class ProductServiceAdapter {

    public String getSkuNameById(Long skuId) {
        //远程调用
        return "洗衣液";
    }

}
