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
    List<DictValueData> getDictValues(@RequestParam("type") String type);

    default List<DictValueData> getPosType() {
        return getDictValues(DictionaryType.POS_TYPE.name());
    }

    default List<DictValueData> getChannel() {
        return getDictValues(DictionaryType.CHANNEL.name());
    }
    default List<DictValueData> getProductAttr() {
        return getDictValues(DictionaryType.PRODUCT_ATTR.name());
    }
    default List<DictValueData> getSkuSpec() {
        return getDictValues(DictionaryType.SKU_SPEC.name());
    }

    default List<DictValueData> getUnit() {
        return getDictValues(DictionaryType.UNIT.name());
    }

    default List<DictValueData> getApprovedStatus() {
        return getDictValues(DictionaryType.APPROVED_STATUS.name());
    }

    default List<DictValueData> getPriceType() {
        return getDictValues(DictionaryType.PRICE_TYPE.name());
    }

    default List<DictValueData> getPosClassify() {
        return getDictValues(DictionaryType.POS_CLASSIFY.name());
    }
    default List<DictValueData> getPosStatus() {
        return getDictValues(DictionaryType.POS_STATUS.name());
    }
    default List<DictValueData> getBaseStore() {
        return getDictValues(DictionaryType.BASESTORE.name());
    }
    default List<DictValueData> getOrderStatus() {
        return getDictValues(DictionaryType.ORDER_STATUS.name());
    }
    default List<DictValueData> getOrderType() {
        return getDictValues(DictionaryType.ORDER_TYPE.name());
    }
    default List<DictValueData> getPlatform() {
        return getDictValues(DictionaryType.PLATFORM.name());
    }
    default List<DictValueData> getOrderSource() {
        return getDictValues(DictionaryType.ORDER_SOURCE.name());
    }
    default List<DictValueData> getDeliveryType() {
        return getDictValues(DictionaryType.DELIVERY_TYPE.name());
    }
    default List<DictValueData> getCarrier() {
        return getDictValues(DictionaryType.CARRIER.name());
    }
    default List<DictValueData> getInvoiceType() {
        return getDictValues(DictionaryType.INVOICE_TYPE.name());
    }
    default List<DictValueData> getPaymentType() {
        return getDictValues(DictionaryType.PAYMENT_TYPE.name());
    }
    default List<DictValueData> getRouterRuleType() {
        return getDictValues(DictionaryType.ROUTER_RULE_TYPE.name());
    }
    default List<DictValueData> getConsignmentStatus() {
        return getDictValues(DictionaryType.CONSIGNMENT_STATUS.name());
    }
}
