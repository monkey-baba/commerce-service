package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.adapter.PosAddressAdapter;
import com.mbb.stock.adapter.PosServiceAdapter;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.service.ReservoirAreaService;
import com.mbb.stock.common.dto.StoreInfoDto;
import com.mbb.stock.common.dto.StoreDetailData;
import com.mbb.stock.rest.dto.StoreUpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservoirarea")
public class ReservoirAreaController extends BaseController{
    @Autowired
    private ReservoirAreaService reservoirAreaService;

    @Autowired
    private PosServiceAdapter posServiceAdapter;

    @Autowired
    private PosAddressAdapter posAddressAdapter;


    @GetMapping("/classify")
    public ResponseEntity reservoirAreaClassification() {
        List<DictValueData> dictValueDataList = posServiceAdapter.getDcClassify();
        return ResponseEntity.ok(dictValueDataList);
    }

    @GetMapping("/status")
    public ResponseEntity reservoirAreaStatus() {
        List<DictValueData> dictValueDataList = posServiceAdapter.getPosStatus();
        return ResponseEntity.ok(dictValueDataList);
    }

    @PostMapping("/info")
    public ResponseEntity getReservoirAreas(@RequestBody StoreListQuery storeListQuery) {
        PointOfServiceModel reservoirAreaModel = new PointOfServiceModel();
        reservoirAreaModel.setName(storeListQuery.getName());
        reservoirAreaModel.setCode(storeListQuery.getCode());
        if(!(storeListQuery.getClassification().equals(""))){
            reservoirAreaModel.setClassifyId(Long.valueOf(storeListQuery.getClassification()));
        }
        if(!(storeListQuery.getStatus().equals(""))){
            reservoirAreaModel.setStatusId(Long.valueOf(storeListQuery.getStatus()));
        }
        reservoirAreaModel.setOwner(storeListQuery.getPeople());
        //开启分页
        PageHelper.startPage(storeListQuery.getPageNum(), storeListQuery.getPageSize());
        //查询数据
        List<PointOfServiceModel> stocks = reservoirAreaService.getReservoirAreas(reservoirAreaModel);
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
    public ResponseEntity addReservoirArea(@RequestBody StoreInfoDto stockInfoDtoList) {
        AddressData addressData=new AddressData();
        addressData.setAddress(stockInfoDtoList.getPaddress());
        addressData.setDetail(stockInfoDtoList.getDetailaddress());
        addressData.setPhone(stockInfoDtoList.getContact());
        addressData.setName(stockInfoDtoList.getOwner());
        Long address= posAddressAdapter.saveAddress(addressData);
        stockInfoDtoList.setAddress(address);
        reservoirAreaService.addReservoirArea(stockInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    private List<StoreInfoDto> dealResult(PageInfo<PointOfServiceModel> stores) {
        List<StoreInfoDto> storeInfoRespList = stores.getList().stream().map(store -> {
            StoreInfoDto storeInfoResp = new StoreInfoDto();
            //大仓地址
            Long address=store.getAddressId();
            AddressData addressData  =  posAddressAdapter.getAddress(address);
            storeInfoResp.setPaddress(addressData.getAddress());
            storeInfoResp.setDetailaddress(addressData.getDetail());
            storeInfoResp.setAddress(address);
            //大仓名字
            storeInfoResp.setName(store.getName());
            //大仓状态
            Long status=store.getStatusId();
            //大仓id
            storeInfoResp.setId(store.getId());
            storeInfoResp.setCode(store.getCode());
            //大仓联系方式
            storeInfoResp.setContact(store.getContact());
            storeInfoResp.setStatus(status);
            String name =posServiceAdapter.getDictValueName(status);
            storeInfoResp.setPstatus(name);
            //大仓负责人
            storeInfoResp.setOwner(String.valueOf(store.getOwner() == null ? "" : store.getOwner()));
            return storeInfoResp;
        }).collect(Collectors.toList());
        return storeInfoRespList;
    }

    @PostMapping("/update")
    public ResponseEntity updateReservoirArea(@RequestBody StoreUpdateData data)  {
        AddressData addressData=new AddressData();
        addressData.setAddress(data.getPaddress());
        addressData.setDetail(data.getDetailaddress());
        addressData.setPhone(data.getContact());
        addressData.setName(data.getOwner());
        PointOfServiceModel reservoirArea=  reservoirAreaService.findById(data.getId());
        Long address= posAddressAdapter.saveAddress(addressData);
        reservoirArea.setAddressId(address);
        reservoirArea.setName(data.getName());
        reservoirArea.setOwner(data.getOwner());
        reservoirArea.setStatusId(data.getStatus());
        reservoirAreaService.updateReservoirArea(reservoirArea);
        return ResponseEntity.ok("更新成功");
    }

    @GetMapping("/detail")
    public ResponseEntity detailReservoirArea(@RequestParam Long id) {
        PointOfServiceModel store=  reservoirAreaService.findById(id);
        StoreDetailData storeDetailData=new StoreDetailData();
        storeDetailData.setCode(store.getCode());
        storeDetailData.setName(store.getName());
        //库区状态
        Long status=store.getStatusId();
        List<DictValueData> dictValueDataList = posServiceAdapter.getPosStatus();
        for(DictValueData dictValueData:dictValueDataList){
            if(status.equals(dictValueData.getId())){
                storeDetailData.setPstatus(dictValueData.getName());
            }
        }
        //大仓分类
        Long classifyId=store.getClassifyId();
        List<DictValueData> posClassifyDataList = posServiceAdapter.getPosClassify();
        for(DictValueData dictValueData:posClassifyDataList){
            if(classifyId.equals(dictValueData.getId())){
                storeDetailData.setClassifyId(dictValueData.getName());
            }
        }
        storeDetailData.setOwner(store.getOwner());
        storeDetailData.setContact(store.getContact());

        //大仓地址
        Long address=store.getAddressId();
        AddressData addressData  =  posAddressAdapter.getAddress(address);
        storeDetailData.setPaddress(addressData.getAddress());
        storeDetailData.setDetailaddress(addressData.getDetail());
        return ResponseEntity.ok(storeDetailData);
    }

}
