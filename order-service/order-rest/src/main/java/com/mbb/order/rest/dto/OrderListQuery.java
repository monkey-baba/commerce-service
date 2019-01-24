package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:06
 */
@Data
public class OrderListQuery {
    private String ecsOrderId;
    private String code;
    private String consignmentCode;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long wareId;
    private Double totalPriceMin;
    private Double totalPriceMax;
    private Date startDate;
    private Date endDate;
    private Date paymentStartDate;
    private Date paymentEndDate;
    private List<Long> statusId;
    private List<Long> newStatusId;
    private List<Long> orderTypeId;
    private List<Long> newOrderTypeId;
    private Integer pageNum;
    private Integer pageSize;
}
