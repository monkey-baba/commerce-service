package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "price_row")
public class PriceRowModel extends BaseModel {

    private Long skuId;
    private Long priceTypeId;
    private Double price;
    private Date startTime;
    private Date endTime;


}
