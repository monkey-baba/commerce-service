package com.mbb.product.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.mbb.product.biz.dao.CategoryMapper;
import com.mbb.product.biz.data.CategoryData;
import com.mbb.product.biz.data.CategoryQuery;
import com.mbb.product.biz.model.CategoryModel;
import com.mbb.product.biz.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public PageInfo<CategoryData> getCategories(CategoryQuery categoryQuery) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCode(categoryQuery.getCode());
        categoryModel.setName(categoryQuery.getName());
        //开启分页
        PageHelper.startPage(categoryQuery.getPageNum(), categoryQuery.getPageSize());
        //查询数据
        List<CategoryModel> categorys = getCategories(categoryModel);
        //获取页码等信息
        PageInfo<CategoryModel> origin = PageInfo.of(categorys);
        //从model转data
        List<CategoryData> categoryDataList = dealResult(origin.getList());
        //用data生成新的分页数据
        PageInfo<CategoryData> result = PageInfo.of(categoryDataList);
        result.setTotal(origin.getTotal());
        return result;
    }

    @Override
    public List<CategoryModel> getCategories(CategoryModel categoryModel) {
        //封装查询Example
        Example example = mapQueryInfo(categoryModel);
        List<CategoryModel> categoryModels = categoryMapper.selectByExample(example);
        LOG.info("category size====" + categoryModels.size());
        return categoryModels;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CategoryModel findCategoryById(Long id) {
        return this.categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatecategory(CategoryModel category) throws Exception {
        int result = this.categoryMapper.updateByPrimaryKey(category);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public CategoryModel createCategory(CategoryModel category) {
        this.categoryMapper.insert(category);
        return category;
    }

    private Example mapQueryInfo(CategoryModel categoryModel) {
        //分类编号
        String code = categoryModel.getCode();
        //分类名称
        String name = categoryModel.getName();
        //父分类id
        Long  parentId= categoryModel.getParentId();
        Example example = new Example(CategoryModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (parentId!=null) {
            criteria.andEqualTo("parentId", parentId);
        }else {
            criteria.andIsNull("parentId");
        }
        return example;
    }


    private List<CategoryData> dealResult(List<CategoryModel> categories) {
        List<CategoryData> categoryDataList = categories.stream().map(categoryModel -> {
            CategoryData categoryData = new CategoryData();
            //分类编码
            categoryData.setCode(categoryModel.getCode());
            //分类名称
            categoryData.setName(categoryModel.getName());
            //父分类编码
            categoryData.setId(categoryModel.getId());

            CategoryModel subCategory = new CategoryModel();
            subCategory.setParentId(categoryModel.getId());
            List<CategoryModel> subCategories = getCategories(subCategory);
            if (!subCategories.isEmpty()) {
                categoryData.setChildren(dealResult(subCategories));
            }

            return categoryData;
        }).collect(Collectors.toList());
        return categoryDataList;
    }
}
