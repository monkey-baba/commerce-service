package com.mbb.order.rest.dto;

import java.util.List;
import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 23:20
 */
@Data
public class OrderCreateData {
    private Long orderType;
    private String code;
    private Long platform;
    private Long store;
    private Long deliveryType;
    private Long carrier;
    private Long customer;
    private Long pos;
    private String receiver;
    private String receiverPhone;
    private String address;
    private List<String> pcd;
    private String remark;
    private String sellerRemark;
    private Boolean invoice;
    private Long invoiceType;
    private String invoiceTitle;
    private Double totalPrice;
    private Double deliveryCost;
    private Double paymentTotal;
    private List<OrderPaymentCreateData> payments;
    private List<OrderEntryCreateData> entries;

}
