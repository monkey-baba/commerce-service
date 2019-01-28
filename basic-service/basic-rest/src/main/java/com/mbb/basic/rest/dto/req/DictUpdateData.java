package com.mbb.basic.rest.dto.req;

import com.mbb.basic.common.dto.DictValueData;
import java.util.List;
import lombok.Data;

@Data
public class DictUpdateData {

    private Long id;

    private String code;

    private String name;

    private List<DictValueData> add;
    private List<DictValueData> update;
    private List<DictValueData> delete;
}
