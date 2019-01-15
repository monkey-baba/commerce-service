package com.mbb.oauth.app.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.math.BigInteger;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter converter=new FastJsonHttpMessageConverter();
        //创建Fastjosn对象并设定序列化规则
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        SerializeConfig globalInstance = SerializeConfig.globalInstance;
        globalInstance.put(Long.class, ToStringSerializer.instance);
        globalInstance.put(Long.TYPE,ToStringSerializer.instance);
        globalInstance.put(BigInteger.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(globalInstance);
        //设定json格式且编码为UTF-8
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));

        //规则赋予转换对象
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
