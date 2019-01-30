package com.mbb.order.rest.dto;

import java.util.Map;
import lombok.Data;

@Data
public class OrderEntryData {

    private Long skuId;
    private String code;
    private String name;
    private Map<String,String> meta;
    private Long quantity;
    private Long shippedQuantity;
    private Double basePrice;
    private Double discount;
    private Double sellPrice;
    private Double totalPrice;
}
