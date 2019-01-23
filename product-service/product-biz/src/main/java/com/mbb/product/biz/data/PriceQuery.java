package com.mbb.product.biz.data;

import lombok.Data;

@Data
public class PriceQuery {
    private String name;
    private Long channelId;
    private Integer pageNum;
    private Integer pageSize;
}
