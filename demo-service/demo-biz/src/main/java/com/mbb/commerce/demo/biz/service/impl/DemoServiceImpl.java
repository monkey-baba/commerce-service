package com.mbb.commerce.demo.biz.service.impl;

import com.mbb.commerce.demo.biz.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoServiceImpl  implements DemoService {


    @Override
    public String demo() {
        // Service方法可以调用Mybatis Dao 实现 数据库逻辑，此处忽略
        return "This is a demo";
    }
}
