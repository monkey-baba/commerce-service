package com.mbb.product.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.product.adapter.ProductServiceAdapter;
import com.mbb.product.biz.model.SkuMetaModel;
import com.mbb.product.biz.model.SkuModel;
import com.mbb.product.biz.service.SkuMetaService;
import com.mbb.product.biz.service.SkuService;
import com.mbb.product.common.dto.SkuData;
import com.mbb.product.common.dto.SkuMetaData;
import com.mbb.product.rest.data.SkuCreateData;
import com.mbb.product.rest.data.SkuUpdateData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sku")
@Slf4j
public class SkuController extends BaseController {

    @Autowired
    private IdService idService;
    @Autowired
    private ProductServiceAdapter productServiceAdapter;
    @Resource
    private SkuService skuService;

    @Autowired
    private SkuMetaService skuMetaService;

    @GetMapping("/list")
    public ResponseEntity getSkus(@RequestParam("code") String skuId,
            @RequestParam("name") String skuName) {
        SkuModel sku = new SkuModel();
        sku.setCode(skuId);
        sku.setName(skuName);
        //查询数据
        List<SkuModel> skus = skuService.getSkus(sku);
        //从model转data
        List<SkuData> result = dealResult(skus);

        return ResponseEntity.ok(result);
    }


    /**
     * 分页获取SKU详情
     */
    @GetMapping("/page")
    public ResponseEntity page(@RequestParam("code") String code,
            @RequestParam("name") String name, @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        SkuModel sku = new SkuModel();
        sku.setCode(code);
        sku.setName(name);
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //查询数据
        List<SkuModel> stocks = skuService.getSkus(sku);
        //获取页码等信息
        PageInfo<SkuModel> origin = PageInfo.of(stocks);
        //从model转data
        List<SkuData> stockInfoRespList = dealResult(origin.getList());
        //用data生成新的分页数据
        PageInfo<SkuData> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{skuId}")
    public ResponseEntity getSkus(@PathVariable("skuId") String skuId) {
        SkuModel sku = new SkuModel();
        sku.setCode(skuId);
        //查询数据
        SkuModel skuModel = skuService.findSkuByCode(skuId);
        //从model转data
        SkuData result = dealResult(skuModel);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/speclist")
    public ResponseEntity getSkuSpecList() {
        List<DictValueData> skuSpecList = productServiceAdapter.getSkuSpec();
        return ResponseEntity.ok(skuSpecList);
    }


    @PostMapping("/update")
    public ResponseEntity updateSku(@RequestBody SkuUpdateData data) throws Exception {
        SkuModel sku = skuService.findSkuById(data.getId());
        sku.setCode(data.getSkuId());
        sku.setName(data.getSkuName());
        skuService.updateSku(sku);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SkuCreateData data) {
        SkuModel skuModel = new SkuModel();
        skuModel.setCode(data.getSkuId());
        skuModel.setName(data.getSkuName());
        skuModel.setId(idService.genId());
        skuService.createSku(skuModel);
        return ResponseEntity.ok(dealResult(skuModel));
    }

    private List<SkuData> dealResult(List<SkuModel> skus) {
        return skus.stream().map(this::dealResult).collect(Collectors.toList());
    }

    private SkuData dealResult(SkuModel skuModel) {

        SkuData skuData = new SkuData();
        //sku编号
        skuData.setCode(skuModel.getCode());
        //sku名称
        skuData.setName(skuModel.getName());
        //id
        skuData.setId(skuModel.getId());
        ArrayList<SkuMetaData> metas = new ArrayList<>();
        skuData.setMeta(metas);
        //Meta
        Map<String, String> meta = skuModel.getMeta();
        //转化成long string 用来展示
        if (meta != null && !meta.isEmpty()){
            meta.forEach((specId, metaId) -> {
                SkuMetaData metaData = new SkuMetaData();
                metaData.setSpecId(specId);
                metaData.setMetaId(metaId);
                SkuMetaModel model = skuMetaService.getSkuMetaById(Long.valueOf(metaId));
                if (model!=null){
                    metaData.setMeta(model.getName());
                }
                metas.add(metaData);
            });
        }

        return skuData;

    }
}
