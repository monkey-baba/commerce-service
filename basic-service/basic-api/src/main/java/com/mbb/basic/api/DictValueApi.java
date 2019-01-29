package com.mbb.basic.api;

import com.mbb.basic.common.dto.DictValueData;
import com.mbb.basic.common.enumation.DictionaryType;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "basic")
public interface DictValueApi {

    /**
     * 示例:调用自己的接口
     *
     * @return result
     */
    @GetMapping("/api/v1/dict/values")
    List<DictValueData> getDictValues(@RequestParam("type") String type,@RequestParam("active") Boolean active);

    @GetMapping("/api/v1/dict/value")
    DictValueData getDictValue(@RequestParam("id")Long id);

    default List<DictValueData> getPosType() {
        return getDictValues(DictionaryType.POS_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getChannel() {
        return getDictValues(DictionaryType.CHANNEL.name(), Boolean.TRUE);
    }

    default List<DictValueData> getProductAttr() {
        return getDictValues(DictionaryType.PRODUCT_ATTR.name(), Boolean.TRUE);
    }

    default List<DictValueData> getSkuSpec() {
        return getDictValues(DictionaryType.SKU_SPEC.name(), Boolean.TRUE);
    }

    default List<DictValueData> getUnit() {
        return getDictValues(DictionaryType.UNIT.name(), Boolean.TRUE);
    }

    default List<DictValueData> getApprovedStatus() {
        return getDictValues(DictionaryType.APPROVED_STATUS.name(), Boolean.TRUE);
    }

    default List<DictValueData> getPriceType() {
        return getDictValues(DictionaryType.PRICE_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getPosClassify() {
        return getDictValues(DictionaryType.POS_CLASSIFY.name(), Boolean.TRUE);
    }

    default List<DictValueData> getPosStatus() {
        return getDictValues(DictionaryType.POS_STATUS.name(), Boolean.TRUE);
    }

    default List<DictValueData> getBaseStore() {
        return getDictValues(DictionaryType.BASESTORE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getOrderStatus() {
        return getDictValues(DictionaryType.ORDER_STATUS.name(), Boolean.TRUE);
    }

    default List<DictValueData> getOrderType() {
        return getDictValues(DictionaryType.ORDER_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getPlatform() {
        return getDictValues(DictionaryType.PLATFORM.name(), Boolean.TRUE);
    }

    default List<DictValueData> getOrderSource() {
        return getDictValues(DictionaryType.ORDER_SOURCE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getDeliveryType() {
        return getDictValues(DictionaryType.DELIVERY_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getCarrier() {
        return getDictValues(DictionaryType.CARRIER.name(), Boolean.TRUE);
    }

    default List<DictValueData> getInvoiceType() {
        return getDictValues(DictionaryType.INVOICE_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getPaymentType() {
        return getDictValues(DictionaryType.PAYMENT_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getRouterRuleType() {
        return getDictValues(DictionaryType.ROUTER_RULE_TYPE.name(), Boolean.TRUE);
    }

    default List<DictValueData> getConsignmentStatus() {
        return getDictValues(DictionaryType.CONSIGNMENT_STATUS.name(), Boolean.TRUE);
    }

    default List<DictValueData> getCustomerStatus() {
        return getDictValues(DictionaryType.CUS_STATUS.name(), Boolean.TRUE);
    }

    default List<DictValueData> getDcClassify() {
        return getDictValues(DictionaryType.DC_CLASSIFY.name(), Boolean.TRUE);
    }
}
