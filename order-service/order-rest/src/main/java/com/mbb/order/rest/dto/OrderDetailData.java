package com.mbb.order.rest.dto;

import java.util.Date;
import lombok.Data;

@Data
public class OrderDetailData {

    private String ecsOrderId;
    private String code;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long posId;
    private Double totalPrice;
    private Double deliveryCost;
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
