package com.mbb.product.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.product.rest.data.category.CategoryCreateData;
import com.mbb.product.rest.data.category.CategoryData;
import com.mbb.product.rest.data.category.CategoryQuery;
import com.mbb.product.rest.data.category.CategoryUpdateData;
import com.mbb.product.biz.model.CategoryModel;
import com.mbb.product.biz.service.CategoryService;
import com.mbb.product.rest.data.category.CategoryDeleteData;
import com.mbb.product.rest.data.category.SubCategoryCreateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/category")
@Slf4j
public class CategoryController extends BaseController {
    @Resource
    private CategoryService categoryService;
    @Autowired
    private IdService idService;
    @GetMapping("/list")
    public ResponseEntity getCategorys(CategoryQuery categoryQuery) {

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCode(categoryQuery.getCode());
        categoryModel.setName(categoryQuery.getName());
        //开启分页
        PageHelper.startPage(categoryQuery.getPageNum(), categoryQuery.getPageSize());
        //用data生成新的分页数据
        PageInfo<CategoryData> result = PageInfo.of(dealResult(categoryService.getCategories(categoryModel)));
        result.setTotal(result.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity updatecategory(@RequestBody CategoryUpdateData data) throws Exception {
        CategoryModel category = categoryService.findCategoryById(data.getId());
        category.setName(data.getName());
        categoryService.updatecategory(category);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody CategoryCreateData data) {
        CategoryModel categoryModel = new CategoryModel();
        Long id = idService.genId();
        categoryModel.setCode(String.valueOf(id));
        categoryModel.setName(data.getName());
        categoryModel.setId(id);
        categoryService.createCategory(categoryModel);
        return ResponseEntity.ok(dealResult(categoryModel));
    }

    @PostMapping("/create/sub")
    public ResponseEntity createSub(@RequestBody SubCategoryCreateData data) {
        CategoryModel categoryModel = new CategoryModel();
        //id
        Long id = idService.genId();
        categoryModel.setCode(String.valueOf(id));
        categoryModel.setName(data.getName());
        categoryModel.setId(id);
        categoryModel.setParentId(data.getParentId());
        categoryService.createCategory(categoryModel);
        return ResponseEntity.ok(dealResult(categoryModel));
    }

    @PostMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestBody CategoryDeleteData categoryDeleteData) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setParentId(categoryDeleteData.getId());
        List<CategoryModel> subCategories = categoryService.getCategories(categoryModel);
        if (!subCategories.isEmpty()){
            return ResponseEntity.ok("该分类下边有子分类，不可以删除");
        }
        categoryService.deleteCategory(categoryDeleteData.getId());
        return ResponseEntity.ok("删除成功");
    }

    private List<CategoryData> dealResult(List<CategoryModel> categories) {
        List<CategoryData> categoryDataList = categories.stream().map(categoryModel -> dealResult(categoryModel)).collect(Collectors.toList());
        return categoryDataList;
    }

    private CategoryData dealResult(CategoryModel categoryModel) {

        CategoryData categoryData = new CategoryData();
        //分类编码
        categoryData.setCode(categoryModel.getCode());
        //分类名称
        categoryData.setName(categoryModel.getName());
        //父分类编码
        categoryData.setId(categoryModel.getId());

        CategoryModel subCategory = new CategoryModel();
        subCategory.setParentId(categoryModel.getId());
        List<CategoryModel> subCategories = categoryService.getCategories(subCategory);
        if (!subCategories.isEmpty()) {
            categoryData.setChildren(dealResult(subCategories));
        }

        return categoryData;
    }
}
