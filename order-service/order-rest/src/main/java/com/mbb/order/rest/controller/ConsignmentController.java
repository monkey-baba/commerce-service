package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.adapter.AddressAdapter;
import com.mbb.order.adapter.DictAdapter;
import com.mbb.order.biz.model.ConsignmentModel;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.service.ConsignmentService;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.ConsignmentInfoResp;
import com.mbb.order.rest.dto.ConsignmentListQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            target.setStoreName(getName(orderModel.getStoreId()));
            AddressData address = addressAdapter.getAddress(orderModel.getAddressId());
            if (address != null) {
                target.setReceiverAddress(address.getDetail());
            }
        }
        target.setCode(source.getCode());
        target.setConsignmentStatusName(getName(source.getStatusId()));
        target.setPosName(getName(source.getPosId()));
        target.setExpressNum(source.getExpressNum());
        target.setDeliveryDate(source.getDate());
    }

    private String getName(Long id) {
        DictValueData dictValue = dictAdapter.getDictValue(id);
        if (dictValue != null) {
            return dictValue.getName();
        }
        return StringUtils.EMPTY;
    }
}
