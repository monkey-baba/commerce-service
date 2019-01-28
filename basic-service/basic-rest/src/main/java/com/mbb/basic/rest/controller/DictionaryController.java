package com.mbb.basic.rest.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.service.DictionaryService;
import com.mbb.basic.biz.service.DictionaryValueService;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.basic.rest.dto.req.DictCreateData;
import com.mbb.basic.rest.dto.req.DictDeleteData;
import com.mbb.basic.rest.dto.req.DictListQuery;
import com.mbb.basic.rest.dto.req.DictUpdateData;
import com.mbb.basic.rest.dto.resp.DictListResp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dict")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;


    @Autowired
    private DictionaryValueService dictionaryValueService;

    @Autowired
    private IdService idService;

    /**
     * 根据Type获取枚举值
     *
     * @param type 枚举类型字符串
     * @return 枚举值List
     */
    @GetMapping("/values")
    public ResponseEntity values(@RequestParam String type,@RequestParam(required = false) Boolean active) {

        List<DictionaryValueModel> values = dictionaryService.findDictValues(type,active);
        List<DictValueData> result = values.stream().map(value -> {
            DictValueData dictValueData = new DictValueData();
            BeanCopier.create(DictionaryValueModel.class, DictValueData.class, false)
                    .copy(value, dictValueData, null);
            return dictValueData;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/value")
    public ResponseEntity value(@RequestParam Long id) {
        DictionaryValueModel value = dictionaryValueService.findById(id);
        DictValueData dictValueData = new DictValueData();
        BeanCopier.create(DictionaryValueModel.class, DictValueData.class, false)
                .copy(value, dictValueData, null);
        return ResponseEntity.ok(dictValueData);
    }


    /**
     * 枚举列表
     */
    @GetMapping("/list")
    public ResponseEntity list(DictListQuery query) {
        DictionaryModel model = new DictionaryModel();
        model.setCode(query.getCode());
        model.setName(query.getName());

        //开启分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        //查询数据
        List<DictionaryModel> users = dictionaryService.findDictByExample(model);
        //获取页码等信息
        PageInfo<DictionaryModel> origin = PageInfo.of(users);

        //Model转Data
        List<DictListResp> list = origin.getList().stream().map(dict -> {
            DictListResp info = new DictListResp();
            info.setId(dict.getId());
            info.setName(dict.getName());
            info.setCode(dict.getCode());
            info.setEditable(dict.getEditable());
            return info;
        }).collect(Collectors.toList());

        //用data生成新的分页数据
        PageInfo<DictListResp> result = PageInfo.of(list);
        //把原来的总条数复制进去
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);

    }



    @PostMapping("/create")
    public ResponseEntity create(@RequestBody DictCreateData data) {
        DictionaryModel dict = new DictionaryModel();
        dict.setId(idService.genId());
        dict.setCode(data.getCode());
        dict.setName(data.getName());
        dict.setEditable(Boolean.TRUE);
        dict.setVersion(0);
        dictionaryService.createDict(dict);
        DictListResp resp = new DictListResp();
        resp.setId(dict.getId());
        resp.setCode(dict.getCode());
        resp.setName(dict.getName());
        resp.setEditable(dict.getEditable());
        return ResponseEntity.ok(resp);
    }


    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody DictUpdateData data) {
        DictionaryModel dict = dictionaryService.findDictById(data.getId());
        if (dict ==null){
            return ResponseEntity.badRequest().body("找不到对应枚举");
        }
        dict.setName(data.getName());

        List<DictionaryValueModel> add  =data.getAdd().stream().map(v->{
            DictionaryValueModel value=new DictionaryValueModel();
            BeanCopier.create(DictValueData.class, DictionaryValueModel.class, false)
                    .copy(v, value, null);
            value.setId(idService.genId());
            value.setVersion(1);
            value.setTypeId(dict.getId());
            return value;
        }).collect(Collectors.toList());

        List<DictionaryValueModel> update  =data.getUpdate().stream().map(v->{
            DictionaryValueModel value=new DictionaryValueModel();
            BeanCopier.create(DictValueData.class, DictionaryValueModel.class, false)
                    .copy(v, value, null);
            return value;
        }).collect(Collectors.toList());

        List<DictionaryValueModel> delete  =data.getDelete().stream().map(v->{
            DictionaryValueModel value=new DictionaryValueModel();
            BeanCopier.create(DictValueData.class, DictionaryValueModel.class, false)
                    .copy(v, value, null);
            return value;
        }).collect(Collectors.toList());


        // 为了在一个事务里面执行，所以一个方法处理所有
        dictionaryService.updateDictAndValues(dict,add,update,delete);
        return ResponseEntity.ok("更新成功");
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody List<DictDeleteData> datas) {
        dictionaryService.deleteDicts(
                datas.stream().map(DictDeleteData::getId).collect(Collectors.toList()));
        return ResponseEntity.ok("删除成功");
    }
}
