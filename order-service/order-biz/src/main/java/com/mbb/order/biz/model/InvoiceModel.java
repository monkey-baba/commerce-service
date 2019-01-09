package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.order.biz.enumation.InvoiceType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "invoice")
public class InvoiceModel extends BaseModel {

    private Boolean applied;
    private InvoiceType type;
    private String title;
    private Double amount;
    private Long orderId;
}
