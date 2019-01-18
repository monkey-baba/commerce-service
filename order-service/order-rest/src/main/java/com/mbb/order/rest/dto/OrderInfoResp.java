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
    private String code;
    private Long wareId;
    private Long orderTypeId;
    private Long statusId;
    private Double totalPrice;
    private String receiver;
    private String receiverPhone;
    private Long addressId;
    private Date date;
    private Date paymentDate;
}
