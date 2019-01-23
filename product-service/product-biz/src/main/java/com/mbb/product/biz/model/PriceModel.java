package com.mbb.product.biz.model;

import com.mbb.common.handler.ListTypeHandler;
import com.mbb.common.model.BaseModel;
import java.util.Date;
import java.util.List;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.ColumnType;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "price")
public class PriceModel extends BaseModel {
    private String name;
    private Long productId;
    @ColumnType(typeHandler = ListTypeHandler.class)
    private List<Long> channelId;
    private Boolean active;
    private Date startTime;
    private Date endTime;
    private Integer priority;


}
