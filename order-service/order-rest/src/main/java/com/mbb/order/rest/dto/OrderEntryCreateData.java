package com.mbb.order.rest.dto;

import com.mbb.basic.common.dto.DictValueData;
import lombok.Data;

@Data
public class OrderEntryCreateData {
    private Long id;
    private Long quantity;
    private Long shippedQuantity;
    private Double basePrice;
    private Double discount;
    private Double totalPrice;
}
