package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hyx
 * @title ConsignmentListQuery
 * @description
 * @date 2019/1/24
 */
@Data
public class ConsignmentListQuery {
    private String code;
    private String ecsOrderId;
    private String orderCode;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long posId;
    private Double totalPriceMin;
    private Double totalPriceMax;
    private Long carrierId;
    private String expressNum;
    private Long deliveryTypeId;
    private Date deliveryStartDate;
    private Date deliveryEndDate;
    private Date startDate;
    private Date endDate;
    private List<Long> consignmentStatusIds;
    private Integer pageNum;
    private Integer pageSize;
}
