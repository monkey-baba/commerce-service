package com.mbb.auth.biz.service.impl;

import com.mbb.auth.biz.service.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocalServiceImpl  implements LocalService {


    @Override
    public String local() {
        // Service方法可以调用Mybatis Dao 实现 数据库逻辑，此处忽略
        return "This is a demo";
    }
}
