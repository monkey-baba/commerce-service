package com.mbb.stock.rest.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StoreService;
import com.mbb.stock.biz.service.impl.StockServiceImpl;
import com.mbb.stock.rest.dto.StockInfoResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(StoreController.class);
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

    @PostMapping("/info")
    public ResponseEntity getStores(@RequestBody StoreListQuery storeListQuery) {

        PointOfServiceModel storeModel = new PointOfServiceModel();
        storeModel.setName(storeListQuery.getName());
        storeModel.setCode(storeListQuery.getCode());
        if(!(storeListQuery.getClassification().equals(""))){
            storeModel.setClassifyId(Long.valueOf(storeListQuery.getClassification()));
        }
        if(!(storeListQuery.getStatus().equals(""))){
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

        logger.info("Paddress().size()===="+ JSON.toJSONString(stockInfoDtoList.getPaddress()));
        logger.info("Detailaddress()====" + stockInfoDtoList.getDetailaddress());
        logger.info("stockInfoDtoList.getContact()====" + stockInfoDtoList.getContact());
        AddressData addressData=new AddressData();
        addressData.setAddress(stockInfoDtoList.getPaddress());
        addressData.setDetail(stockInfoDtoList.getDetailaddress());
        addressData.setPhone(stockInfoDtoList.getContact());
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
            storeInfoResp.setAddress(address);
            //门店名字
            storeInfoResp.setName(store.getName());
            //门店状态
            Long status=store.getStatusId();
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
}
