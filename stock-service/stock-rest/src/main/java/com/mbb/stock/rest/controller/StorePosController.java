package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.adapter.PosServiceAdapter;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.model.StorePosModel;
import com.mbb.stock.biz.service.StoreService;
import com.mbb.stock.common.dto.StoreInfoDto;
import com.mbb.stock.common.enumation.PosType;
import com.mbb.stock.rest.dto.DeleteStorePosData;
import com.mbb.stock.rest.dto.PosQuery;
import com.mbb.stock.rest.dto.StorePosData;
import com.mbb.stock.rest.dto.StorePosListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/api/v1/storePos")
public class StorePosController extends BaseController{
    @Autowired
    private StoreService storeService;

    @Autowired
    private PosServiceAdapter posServiceAdapter;

    @GetMapping("/baseStore")
    public ResponseEntity getBaseStore() {
        List<DictValueData> dictValueDataList = posServiceAdapter.getBaseStore();
        return ResponseEntity.ok(dictValueDataList);
    }

    @GetMapping("/posType")
    public ResponseEntity getPosType() {
        List<DictValueData> dictValueDataList = posServiceAdapter.getPosType();
        return ResponseEntity.ok(dictValueDataList);
    }

    @PostMapping("/add")
    public ResponseEntity addReservoirArea(@RequestBody StorePosData storePosData) {
        StorePosModel storePosModel=new StorePosModel();
        storePosModel.setStoreId(storePosData.getStore());
        storePosModel.setPosId(storePosData.getPos());
        storeService.addStorePos(storePosModel);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/pos/list")
    public ResponseEntity getPosList(PosQuery posQuery) {
        PointOfServiceModel storeModel = new PointOfServiceModel();
        storeModel.setName(posQuery.getName());
        storeModel.setCode(posQuery.getCode());
        //开启分页
        PageHelper.startPage(posQuery.getPageNum(), posQuery.getPageSize());
        //查询数据
        List<PointOfServiceModel> stocks = storeService.getPos(storeModel);
        //获取页码等信息
        PageInfo<PointOfServiceModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StoreInfoDto> stockInfoRespList = dealResult(origin);
        //用data生成新的分页数据
        if(null!=stockInfoRespList&& stockInfoRespList.size()>0){
            PageInfo<StoreInfoDto> result = PageInfo.of(stockInfoRespList);
            result.setTotal(origin.getTotal());
            return ResponseEntity.ok(result);
        }else{
            PageInfo<StoreInfoDto> result = null;
            result.setTotal(0);
            return ResponseEntity.ok(result);
        }

    }

    @PostMapping("/info")
    public ResponseEntity getReservoirAreas(@RequestBody StorePosListData storePosListData) {
        StorePosModel storePosModel = new StorePosModel();
        Map<String, Object> parameters = buildParameters(storePosListData);
        /*if(!(storePosListData.getPosName().equals(""))){
            storePosModel.setStoreId(storePosListData.getPosName());
        }*/
        //开启分页
        PageHelper.startPage(storePosListData.getPageNum(), storePosListData.getPageSize());
        //查询数据
        List<StorePosModel> stocks = storeService.getStorePos(parameters);
        //获取页码等信息
        PageInfo<StorePosModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StorePosListData> stockInfoRespList = storePosResult(origin);
        //用data生成新的分页数据
        PageInfo<StorePosListData> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteProduct(@RequestBody List<DeleteStorePosData> datas) {
        for (DeleteStorePosData data : datas) {
            storeService.deleteStorePos(data.getId());
        }
        return ResponseEntity.ok("删除成功");
    }
    private List<StoreInfoDto> dealResult(PageInfo<PointOfServiceModel> stores) {
        List<StoreInfoDto> storeInfoRespList=null;
        if(stores.getList().size()>0){
            storeInfoRespList = stores.getList().stream().map(store -> {
                StoreInfoDto storeInfoResp = new StoreInfoDto();
                //供货点名字
                storeInfoResp.setName(store.getName());
                //供货点id
                storeInfoResp.setId(store.getId());
                //供货点编码
                storeInfoResp.setCode(store.getCode());
                return storeInfoResp;
            }).collect(Collectors.toList());
        };
        return storeInfoRespList;
    }

    private List<StorePosListData> storePosResult(PageInfo<StorePosModel> stores) {
        List<StorePosListData> storePosRespList=null;
        if(stores.getList().size()>0){
            storePosRespList = stores.getList().stream().map(store -> {
                StorePosListData storePosResp = new StorePosListData();
                PointOfServiceModel pointOfServiceModel=  storeService.findById(store.getPosId());
                storePosResp.setId(store.getId());
                if(null!=pointOfServiceModel) {
                    //供货点名字
                    storePosResp.setPosName(pointOfServiceModel.getName());
                    //供货点编码
                    storePosResp.setPosCode(pointOfServiceModel.getCode());
                    PosType posType=pointOfServiceModel.getPosType();
                    String posname= posType.name();
                    if(posname.equals("STORE")){
                        storePosResp.setPosTypeName("门店");
                    }else{
                        storePosResp.setPosTypeName("大仓");
                    }
                }
                //网店名字
                String name =posServiceAdapter.getDictValueName(store.getStoreId());
                storePosResp.setStoreName(name);
                //网店编码
                storePosResp.setStoreCode(store.getStoreId());
                return storePosResp;
            }).collect(Collectors.toList());
        };
        return storePosRespList;
    }
    private Map<String, Object> buildParameters(StorePosListData storePosListData) {
        if (storePosListData == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> parameters = new HashMap<>(18);
        parameters.put("posCode", storePosListData.getPosCode());
        parameters.put("posName", storePosListData.getPosName());
        return parameters;
    }
}
