package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "invoice")
public class InvoiceModel extends BaseModel {

    private Boolean applied;
    private Long typeId;
    private String title;
    private Double amount;
    private Long orderId;
}
