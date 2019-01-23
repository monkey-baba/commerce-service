package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 23:20
 */
@Data
public class OrderCreateData {
    private String ecsOrderId;
    private String code;
    private String consignmentCode;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long wareId;
    private Double totalPrice;
    private Date Date;
    private Date paymentDate;
    private Long statusId;
    private Long orderTypeId;
}
