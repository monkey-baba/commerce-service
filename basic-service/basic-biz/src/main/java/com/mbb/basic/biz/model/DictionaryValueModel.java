package com.mbb.basic.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dictionaryValue_value")
public class DictionaryValueModel extends BaseModel {

    private String code;
    private String name;
    private String description;
    private Long typeId;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private Boolean active;
}
