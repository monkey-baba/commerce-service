package com.mbb.stock.rest.dto;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-16 16:30
 */
@Data
public class WarehouseCreateData {
    private String code;
    private String name;
    private String active;
    private Long posId;
    private String posAddress;
}
