package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order")
public class OrderModel extends BaseModel {

    private String ecsOrderId;
    private String code;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long wareId;
    private Double totalPrice;
    private Double subTotal;
    private Double discount;
    private Date date;
    private Date paymentDate;
    private Long statusId;
    private Long orderTypeId;
    private Long addressId;
    private Long platformId;
    private Long orderSourceId;
    private Long channelId;
    private String remark;
    private Long deliveryTypeId;
    private Long carrierId;


}
