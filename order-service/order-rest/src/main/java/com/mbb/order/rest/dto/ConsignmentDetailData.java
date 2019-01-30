package com.mbb.order.rest.dto;

import com.mbb.basic.common.dto.AddressData;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hyx
 * @title ConsignmentDetailData
 * @description
 * @date 2019/1/29
 */
@Data
public class ConsignmentDetailData {

    private Long orderId;
    private String ecsOrderId;
    private String consignmentCode;
    private String consignmentStatusName;
    private String deliveryTypeName;
    private String customer;
    private Long posId;
    private String posName;
    private String carrierName;
    private String expressNum;
    private Date date;
    private Date deliveryDate;
    private Double totalPrice;
    private Double deliveryCost;
    private String receiver;
    private String receiverPhone;
    private AddressData address;
    private String remark;
    private List<SellerRemarkData> sellerRemarks;
    private List<ConsignmentEntryData> entries;

}
