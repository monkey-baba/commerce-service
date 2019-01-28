package com.mbb.product.biz.service.impl;



import com.mbb.product.biz.dao.CategoryMapper;

import com.mbb.product.biz.model.CategoryModel;
import com.mbb.product.biz.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryModel> getCategories(CategoryModel categoryModel) {
        //封装查询Example
        Example example = mapQueryInfo(categoryModel);
        List<CategoryModel> categoryModels = categoryMapper.selectByExample(example);
        log.info("category size====" + categoryModels.size());
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



}
