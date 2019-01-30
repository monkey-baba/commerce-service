package com.mbb.order.rest.dto;

import java.util.Date;
import lombok.Data;

@Data
public class SellerRemarkData {

    private String user;
    private Date date;
    private String remark;
}
