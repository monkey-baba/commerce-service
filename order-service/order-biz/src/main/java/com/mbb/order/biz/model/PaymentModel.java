package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "payment")
public class PaymentModel extends BaseModel {

    private Date date;
    private Long typeId;
    private Double amount;
    private Long orderId;
    private String serialNumber;
}
