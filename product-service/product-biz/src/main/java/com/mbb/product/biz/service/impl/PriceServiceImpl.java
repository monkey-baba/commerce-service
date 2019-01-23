package com.mbb.product.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.product.biz.dao.PriceMapper;
import com.mbb.product.biz.data.PriceData;
import com.mbb.product.biz.data.PriceQuery;
import com.mbb.product.biz.model.PriceModel;
import com.mbb.product.biz.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PriceServiceImpl implements PriceService {
    private static final Logger LOG = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Resource
    private PriceMapper priceMapper;
    @Override
    public PageInfo<PriceData> getPrices(PriceQuery priceQuery) {
        PriceModel priceModel = new PriceModel();
        priceModel.setName(priceQuery.getName());
        priceModel.setChannelId(priceQuery.getChannelId());

        //开启分页
        PageHelper.startPage(priceQuery.getPageNum(), priceQuery.getPageSize());
        //查询数据
        List<PriceModel> prices = getPrices(priceModel);
        //获取页码等信息
        PageInfo<PriceModel> origin = PageInfo.of(prices);
        //从model转data
        List<PriceData> priceDataList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<PriceData> result = PageInfo.of(priceDataList);
        result.setTotal(origin.getTotal());
        return result;
    }

    public List<PriceModel> getPrices(PriceModel priceModel) {
        //封装查询Example
        Example example = mapQueryInfo(priceModel);
        List<PriceModel> priceModels = priceMapper.selectByExample(example);
        LOG.info("price size====" + priceModels.size());
        return priceModels;
    }

    @Override
    public void deletePrice(Long id) {
        priceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PriceModel findPriceById(Long id) {
        return this.priceMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateprice(PriceModel price) throws Exception {
        int result = this.priceMapper.updateByPrimaryKey(price);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public PriceModel createPrice(PriceModel price) {
        this.priceMapper.insert(price);
        return price;
    }

    private Example mapQueryInfo(PriceModel priceModel) {
        //价目表名称
        String name = priceModel.getName();
        //渠道号
        Long channelId = priceModel.getChannelId();
        Example example = new Example(PriceModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (channelId!=null) {
            criteria.andEqualTo("channelId", channelId);
        }
        return example;
    }


    private List<PriceData> dealResult(PageInfo<PriceModel> prices) {
        List<PriceData> priceDataList = prices.getList().stream().map(priceModel -> {
            PriceData priceData = new PriceData();
            //客户名称
            priceData.setName(priceModel.getName());
            //手机号
            priceData.setChannelId(priceModel.getChannelId());
            //id
            priceData.setId(priceModel.getId());
            //是否启用
            priceData.setActive(priceModel.getActive());
            //开始时间
            priceData.setStartTime(priceModel.getStartTime());
            //结束时间
            priceData.setEndTime(priceModel.getEndTime());
            //
            priceData.setPriority(priceModel.getPriority());
            return priceData;
        }).collect(Collectors.toList());
        return priceDataList;
    }
}
