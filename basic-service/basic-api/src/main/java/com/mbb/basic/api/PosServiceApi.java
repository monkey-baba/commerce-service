package com.mbb.basic.api;

import com.mbb.basic.common.dto.DictValueData;
import com.mbb.basic.common.enumation.DictionaryType;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "basic")
public interface PosServiceApi {

    /**
     * 示例:调用自己的接口
     *
     * @return result
     */
    @GetMapping("/api/v1/dict/values")
    List<DictValueData> getPosType(@RequestParam("type") String type);

    default List<DictValueData> getPosType() {
        return getPosType(DictionaryType.POS_TYPE.name());
    }

}
