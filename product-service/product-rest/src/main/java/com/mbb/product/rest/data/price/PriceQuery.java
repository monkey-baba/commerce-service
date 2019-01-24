package com.mbb.product.rest.data.price;

import lombok.Data;

import java.util.List;

@Data
public class PriceQuery {
    private String name;
    private List<Long> channelId;
    private Integer pageNum;
    private Integer pageSize;
}
