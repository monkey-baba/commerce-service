package com.mbb.basic.common.dto;

import java.util.List;
import lombok.Data;

@Data
public class AddressData {

    /**
     * 前端地址code数组
     */
    private List<String> address;
    /**
     * 街道详细地址
     */
    private String detail;
    /**
     * 联系电话
     */
    private String phone;
}
