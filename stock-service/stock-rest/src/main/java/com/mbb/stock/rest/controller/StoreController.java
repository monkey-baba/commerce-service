package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.biz.service.StoreService;

import com.mbb.stock.common.dto.StoreInfoDto;
import com.mbb.stock.common.dto.StoreDetailData;
import com.mbb.stock.rest.dto.StoreUpdateData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.adapter.PosServiceAdapter;
import com.mbb.stock.adapter.PosAddressAdapter;
@RestController
@RequestMapping("/api/v1/store")
public class StoreController extends BaseController{
    @Autowired
    private StoreService storeService;

    @Autowired
    private PosServiceAdapter posServiceAdapter;

    @Autowired
    private PosAddressAdapter posAddressAdapter;


    @GetMapping("/allList")
    public ResponseEntity storeAllList(StoreListQuery query) {
        List<StoreInfoDto> stockInfoDtoList = storeService.getAllStores();
        return ResponseEntity.ok(stockInfoDtoList);
    }

    @GetMapping("/classify")
    public ResponseEntity storeClassification() {
       List<DictValueData> dictValueDataList = posServiceAdapter.getPosClassify();
        return ResponseEntity.ok(dictValueDataList);
    }

    @GetMapping("/status")
    public ResponseEntity storeStatus() {
        List<DictValueData> dictValueDataList = posServiceAdapter.getPosStatus();
        return ResponseEntity.ok(dictValueDataList);
    }

    @GetMapping("/info")
    public ResponseEntity getStores(StoreListQuery storeListQuery) {
        PointOfServiceModel storeModel = new PointOfServiceModel();
        storeModel.setName(storeListQuery.getName());
        storeModel.setCode(storeListQuery.getCode());
        if(StringUtils.isNotEmpty(storeListQuery.getClassification())){
            storeModel.setClassifyId(Long.valueOf(storeListQuery.getClassification()));
        }
        if(StringUtils.isNotEmpty(storeListQuery.getStatus())){
            storeModel.setStatusId(Long.valueOf(storeListQuery.getStatus()));
        }
        storeModel.setOwner(storeListQuery.getPeople());
        //开启分页
        PageHelper.startPage(storeListQuery.getPageNum(), storeListQuery.getPageSize());
        //查询数据
        List<PointOfServiceModel> stocks = storeService.getStores(storeModel);
        //获取页码等信息
        PageInfo<PointOfServiceModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StoreInfoDto> stockInfoRespList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<StoreInfoDto> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody StoreInfoDto stockInfoDtoList) {
        AddressData addressData=new AddressData();
        addressData.setAddress(stockInfoDtoList.getPaddress());
        addressData.setDetail(stockInfoDtoList.getDetailaddress());
        addressData.setPhone(stockInfoDtoList.getContact());
        addressData.setName(stockInfoDtoList.getOwner());
        Long address= posAddressAdapter.saveAddress(addressData);
        stockInfoDtoList.setAddress(address);
        storeService.addStore(stockInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    private List<StoreInfoDto> dealResult(PageInfo<PointOfServiceModel> stores) {
        List<StoreInfoDto> storeInfoRespList = stores.getList().stream().map(store -> {
            StoreInfoDto storeInfoResp = new StoreInfoDto();
            //门店地址
            Long address=store.getAddressId();
            AddressData addressData  =  posAddressAdapter.getAddress(address);
            storeInfoResp.setPaddress(addressData.getAddress());
            storeInfoResp.setDetailaddress(addressData.getDetail());
            storeInfoResp.setAddress(address);
            //门店名字
            storeInfoResp.setName(store.getName());
            //门店状态
            Long status=store.getStatusId();
            //门店id
            storeInfoResp.setId(store.getId());
            storeInfoResp.setCode(store.getCode());
            //门店联系方式
            storeInfoResp.setContact(store.getContact());
            storeInfoResp.setStatus(status);
            List<DictValueData> dictValueDataList = posServiceAdapter.getPosStatus();
            for(DictValueData dictValueData:dictValueDataList){
                if(status.equals(dictValueData.getId())){
                    storeInfoResp.setPstatus(dictValueData.getName());
                }
            }
            //门店负责人
            storeInfoResp.setOwner(String.valueOf(store.getOwner() == null ? "" : store.getOwner()));
            return storeInfoResp;
        }).collect(Collectors.toList());
        return storeInfoRespList;
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody StoreUpdateData data)  {
        AddressData addressData=new AddressData();
        addressData.setAddress(data.getPaddress());
        addressData.setDetail(data.getDetailaddress());
        addressData.setPhone(data.getContact());
        addressData.setName(data.getOwner());
        PointOfServiceModel store=  storeService.findById(data.getId());
        addressData.setId(store.getAddressId());
        Long address= posAddressAdapter.saveAddress(addressData);
        store.setAddressId(address);
        store.setName(data.getName());
        store.setOwner(data.getOwner());
        store.setStatusId(data.getStatus());
        storeService.updateStore(store);
        return ResponseEntity.ok("更新成功");
    }

    @GetMapping("/detail")
    public ResponseEntity childRole(@RequestParam Long id) {
        PointOfServiceModel store=  storeService.findById(id);
        StoreDetailData storeDetailData=new StoreDetailData();
        storeDetailData.setCode(store.getCode());
        storeDetailData.setName(store.getName());
        //门店状态
        Long status=store.getStatusId();
        List<DictValueData> dictValueDataList = posServiceAdapter.getPosStatus();
        for(DictValueData dictValueData:dictValueDataList){
            if(status.equals(dictValueData.getId())){
                storeDetailData.setPstatus(dictValueData.getName());
            }
        }
        //门店分类
        Long classifyId=store.getClassifyId();
        List<DictValueData> posClassifyDataList = posServiceAdapter.getPosClassify();
        for(DictValueData dictValueData:posClassifyDataList){
            if(classifyId.equals(dictValueData.getId())){
                storeDetailData.setClassifyId(dictValueData.getName());
            }
        }
        storeDetailData.setOwner(store.getOwner());
        storeDetailData.setContact(store.getContact());

        //门店地址
        Long address=store.getAddressId();
        AddressData addressData  =  posAddressAdapter.getAddress(address);
        storeDetailData.setPaddress(addressData.getAddress());
        storeDetailData.setDetailaddress(addressData.getDetail());
        return ResponseEntity.ok(storeDetailData);
    }

}
