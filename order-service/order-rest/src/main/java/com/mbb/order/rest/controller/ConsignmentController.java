package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.order.adapter.*;
import com.mbb.order.biz.model.*;
import com.mbb.order.biz.service.ConsignmentEntryService;
import com.mbb.order.biz.service.ConsignmentService;
import com.mbb.order.biz.service.OrderEntryService;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.*;
import com.mbb.product.common.dto.SkuData;
import com.mbb.product.common.dto.SkuMetaData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author hyx
 * @title ConsignmentController
 * @description
 * @date 2019/1/24
 */
@RestController
@RequestMapping("/api/v1/consignment")
public class ConsignmentController extends BaseController {

    @Autowired
    private ConsignmentService consignmentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DictAdapter dictAdapter;

    @Autowired
    private AddressAdapter addressAdapter;

    @Autowired
    private ConsignmentEntryService consignmentEntryService;

    @Autowired
    private CustomerAdapter customerAdapter;

    @Autowired
    private OrderEntryService orderEntryService;

    @Autowired
    private ProductAdapter productAdapter;

    @Autowired
    private AccountAdapter accountAdapter;

    @GetMapping("/search")
    public ResponseEntity search(ConsignmentListQuery consignmentListQuery) {
        Map<String, Object> parameters = buildParameters(consignmentListQuery);
        //开启分页
        PageHelper.startPage(consignmentListQuery.getPageNum(), consignmentListQuery.getPageSize());
        //查询数据
        List<ConsignmentModel> consignmentModelList = consignmentService.getConsignments(parameters);
        //获取页码等信息
        PageInfo<ConsignmentModel> origin = PageInfo.of(consignmentModelList);
        //从model转data
        List<ConsignmentInfoResp> orderInfoRespList = origin.getList().stream().map(consignmentModel -> {
            ConsignmentInfoResp consignmentInfoResp = new ConsignmentInfoResp();
            convertConsignment(consignmentModel, consignmentInfoResp);
            return consignmentInfoResp;
        }).collect(Collectors.toList());
        //用data生成新的分页数据
        PageInfo<ConsignmentInfoResp> result = PageInfo.of(orderInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail")
    public ResponseEntity detail(@RequestParam Long id) {
        ConsignmentModel consignmentModel = consignmentService.getConsignmentById(id);
        ConsignmentDetailData consignmentDetailData = new ConsignmentDetailData();
        convertDetail(consignmentModel, consignmentDetailData);
        return ResponseEntity.ok(consignmentDetailData);
    }

    private Map<String, Object> buildParameters(ConsignmentListQuery consignmentListQuery) {
        if (consignmentListQuery == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> parameters = new HashMap<>(18);
        parameters.put("code", consignmentListQuery.getCode());
        parameters.put("ecsOrderId", consignmentListQuery.getEcsOrderId());
        parameters.put("orderCode", consignmentListQuery.getOrderCode());
        parameters.put("storeId", consignmentListQuery.getStoreId());
        parameters.put("customerId", consignmentListQuery.getCustomerId());
        parameters.put("receiver", consignmentListQuery.getReceiver());
        parameters.put("receiverPhone", consignmentListQuery.getReceiverPhone());
        parameters.put("posId", consignmentListQuery.getPosId());
        parameters.put("totalPriceMin", consignmentListQuery.getTotalPriceMin());
        parameters.put("totalPriceMax", consignmentListQuery.getTotalPriceMax());
        parameters.put("carrierId", consignmentListQuery.getCarrierId());
        parameters.put("expressNum", consignmentListQuery.getExpressNum());
        parameters.put("deliveryTypeId", consignmentListQuery.getDeliveryTypeId());
        parameters.put("deliveryStartDate", consignmentListQuery.getDeliveryStartDate());
        parameters.put("deliveryEndDate", consignmentListQuery.getDeliveryEndDate());
        parameters.put("startDate", consignmentListQuery.getStartDate());
        parameters.put("endDate", consignmentListQuery.getEndDate());
        parameters.put("consignmentStatusIds", consignmentListQuery.getConsignmentStatusIds());
        return parameters;
    }

    private void convertConsignment(ConsignmentModel source, ConsignmentInfoResp target) {
        target.setId(source.getId());
        OrderModel orderModel = orderService.getOrderById(source.getOrderId());
        if (orderModel != null) {
            target.setEcsOrderId(orderModel.getEcsOrderId());
            target.setReceiver(orderModel.getReceiver());
            target.setReceiverPhone(orderModel.getReceiver());
            target.setDate(orderModel.getDate());
            target.setStoreName(dictAdapter.getDictValueName((orderModel.getStoreId())));
            AddressData address = addressAdapter.getAddress(orderModel.getAddressId());
            if (address != null) {
                target.setReceiverAddress(address.getDetail());
            }
        }
        target.setCode(source.getCode());
        target.setConsignmentStatusName(dictAdapter.getDictValueName((source.getStatusId())));
        target.setPosName(dictAdapter.getDictValueName((source.getPosId())));
        target.setExpressNum(source.getExpressNum());
        target.setDeliveryDate(source.getDate());
    }

    private void convertDetail(ConsignmentModel source, ConsignmentDetailData target) {
        OrderModel orderModel = orderService.getOrderDetailById(source.getOrderId());
        if (orderModel != null) {
            target.setOrderId(orderModel.getId());
            target.setEcsOrderId(orderModel.getEcsOrderId());
            target.setReceiver(orderModel.getReceiver());
            target.setReceiverPhone(orderModel.getReceiver());
            target.setDate(orderModel.getDate());
            target.setAddress(addressAdapter.getAddress(orderModel.getAddressId()));
            target.setDeliveryTypeName(dictAdapter.getDictValueName(orderModel.getDeliveryTypeId()));
            target.setCustomer(customerAdapter.getCustomerName(orderModel.getCustomerId()));
            target.setTotalPrice(orderModel.getTotalPrice());
            target.setDeliveryCost(orderModel.getDeliveryCost());
            target.setRemark(orderModel.getRemark());
            List<SellerRemarkModel> sellerRemarks = orderModel.getSellerRemarks();
            if (CollectionUtils.isNotEmpty(sellerRemarks)) {
                List<SellerRemarkData> remarkDataList = new ArrayList<>();
                for (SellerRemarkModel sellerRemark : sellerRemarks) {
                    SellerRemarkData sellerRemarkData = new SellerRemarkData();
                    sellerRemarkData.setDate(sellerRemark.getDate());
                    sellerRemarkData.setRemark(sellerRemark.getRemark());
                    sellerRemarkData.setUser(accountAdapter.getUserInfo(sellerRemark.getUserId()).get("name"));
                    remarkDataList.add(sellerRemarkData);
                }
                target.setSellerRemarks(remarkDataList);
            }
        }
        target.setConsignmentCode(source.getCode());
        target.setConsignmentStatusName(dictAdapter.getDictValueName(source.getStatusId()));
        target.setPosId(source.getPosId());
        target.setPosName(dictAdapter.getDictValueName(source.getPosId()));
        target.setCarrierName(dictAdapter.getDictValueName(source.getCarrierId()));
        target.setExpressNum(source.getExpressNum());
        target.setDeliveryDate(source.getDate());
        List<ConsignmentEntryModel> consignmentEntryModelList = consignmentEntryService.getConsignmentEntriesByConsignmentId(source.getId());
        if (CollectionUtils.isNotEmpty(consignmentEntryModelList)) {
            List<ConsignmentEntryData> entries = new ArrayList<>();
            for (ConsignmentEntryModel consignmentEntryModel : consignmentEntryModelList) {
                ConsignmentEntryData entryData = new ConsignmentEntryData();
                convertConsignmentEntry(consignmentEntryModel, entryData);
                entries.add(entryData);
            }
            target.setEntries(entries);
        }
    }

    private void convertConsignmentEntry(ConsignmentEntryModel source, ConsignmentEntryData target) {
        OrderEntryModel orderEntryModel = orderEntryService.getEntryById(source.getOrderEntryId());
        if (orderEntryModel != null) {
            Long skuId = orderEntryModel.getSkuId();
            target.setSkuId(skuId);
            SkuData skuData = productAdapter.getSkuById(skuId);
            if (skuData != null) {
                target.setSkuCode(skuData.getCode());
                target.setSkuName(skuData.getName());
                Map<String, String> meta = new HashMap<>();
                for (SkuMetaData skuMetaData : skuData.getMeta()) {
                    meta.put(skuMetaData.getSpecId(), skuMetaData.getMeta());
                }
                target.setMeta(meta);
            }
            target.setQuantity(orderEntryModel.getQuantity());
        }
        target.setShippedQuantity(source.getShippedQuantity());
    }

}
