package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import java.util.Map;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "price")
public class PriceModel extends BaseModel {

    private Long productId;
    private Long channelId;
    private Boolean active;
    private Date startTime;
    private Date endTime;


}
