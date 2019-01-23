package com.mbb.product.biz.data;

import lombok.Data;

import java.util.Date;
@Data
public class PriceData {
    private Long id;
    private String name;
    private Long channelId;
    private Boolean active;
    private Date startTime;
    private Date endTime;
    private Integer priority;

}
