package com.mbb.product.biz.service;


import com.mbb.product.biz.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    List<CategoryModel> getCategories(CategoryModel categoryModel);

    void deleteCategory(Long id);
    CategoryModel findCategoryById(Long id);
    void updatecategory(CategoryModel category) throws Exception;
    CategoryModel createCategory(CategoryModel category);
}
