package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:07
 */
@Data
public class OrderInfoResp {
    private Long id;
    private String ecsOrderId;
    private Long storeId;
    private String storeName;
    private String code;
    private Long wareId;
    private Long orderTypeId;
    private String orderTypeName;
    private Long statusId;
    private String statusName;
    private Double totalPrice;
    private String receiver;
    private String receiverPhone;
    private Long addressId;
    private String addressName;
    private Date date;
    private Date paymentDate;
}
