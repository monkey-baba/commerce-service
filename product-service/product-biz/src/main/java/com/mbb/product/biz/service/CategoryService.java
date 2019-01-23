package com.mbb.product.biz.service;


import com.github.pagehelper.PageInfo;
import com.mbb.product.biz.data.CategoryData;
import com.mbb.product.biz.data.CategoryQuery;
import com.mbb.product.biz.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    PageInfo<CategoryData> getCategories(CategoryQuery categoryQuery);

    List<CategoryModel> getCategories(CategoryModel categoryModel);

    void deleteCategory(Long id);
    CategoryModel findCategoryById(Long id);
    void updatecategory(CategoryModel category) throws Exception;
    CategoryModel createCategory(CategoryModel category);
}
