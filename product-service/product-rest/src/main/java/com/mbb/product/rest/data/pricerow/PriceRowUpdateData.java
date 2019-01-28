package com.mbb.product.rest.data.pricerow;

import lombok.Data;

import java.util.Date;

@Data
public class PriceRowUpdateData {
    private Long id;
    private Long priceTypeId;
    private Double price;
    private Date startTime;
    private Date endTime;
}
