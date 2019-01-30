package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author hyx
 * @title ConsignmentEntryData
 * @description
 * @date 2019/1/29
 */
@Data
public class ConsignmentEntryData {

    private Long skuId;
    private String skuCode;
    private String skuName;
    private Map<String, String> meta;
    private Long quantity;
    private Long shippedQuantity;
}
