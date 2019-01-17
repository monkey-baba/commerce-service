package com.mbb.product.biz.data;

import lombok.Data;

import java.util.Date;
@Data
public class Pricedata {
    private Long productId;
    private Long channelId;
    private Boolean active;
    private Date startTime;
    private Date endTime;
}
