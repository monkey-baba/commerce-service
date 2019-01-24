package com.mbb.product.rest.data.price;

import com.mbb.common.handler.ListTypeHandler;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import java.util.Date;
import java.util.List;

@Data
public class PriceUpdateData {
    private Long id;
    private String name;
    @ColumnType(typeHandler = ListTypeHandler.class)
    private List<Long> channelId;
    private Boolean active;
    private Date startTime;
    private Date endTime;
    private Integer priority;

}
