package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_entry")
public class OrderEntryModel extends BaseModel {

    private Long skuId;
    private Long quantity;
    private Double totalPrice;
    private Double basePrice;
    private Double discount;
    private Long orderId;
}
