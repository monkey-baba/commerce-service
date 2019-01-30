package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author hyx
 * @title ConsignmentInfoResp
 * @description
 * @date 2019/1/24
 */
@Data
public class ConsignmentInfoResp {
    private Long id;
    private String ecsOrderId;
    private String code;
    private String consignmentStatusName;
    private String storeName;
    private String posName;
    private String expressNum;
    private String receiver;
    private String receiverPhone;
    private String receiverAddress;
    private Date date;
    private Date deliveryDate;
}
