package com.mbb.order.rest.dto;

import com.mbb.basic.common.dto.AddressData;
import com.mbb.stock.common.dto.PosDetailData;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class OrderDetailData {

    private String ecsOrderId;
    private String code;
    private String store;
    private String customer;
    private String receiver;
    private String receiverPhone;
    private String pos;
    private Double totalPrice;
    private Double deliveryCost;
    private Double subTotal;
    private Double discount;
    private Date date;
    private Date paymentDate;
    private String status;
    private String orderType;
    private AddressData address;
    private String platform;
    private String orderSource;
    private String channel;
    private String remark;
    private String deliveryType;
    private String carrier;

    private PosDetailData pointPos;
    private InvoiceData invoice;

    private List<SellerRemarkData> sellerRemarks;

    private List<OrderEntryData> entries;
}
