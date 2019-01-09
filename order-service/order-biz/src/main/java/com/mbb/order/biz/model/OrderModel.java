package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.order.biz.enumation.DeliveryType;
import com.mbb.order.biz.enumation.OrderPlatform;
import com.mbb.order.biz.enumation.OrderSource;
import com.mbb.order.biz.enumation.OrderStauts;
import com.mbb.order.biz.enumation.OrderType;
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
    private OrderStauts status;
    private OrderType orderType;
    private Long addressId;
    private OrderPlatform platform;
    private OrderSource orderSource;
    private Long channelId;
    private String remark;
    private DeliveryType deliveryType;
    private Long carrierId;


}
