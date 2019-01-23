package com.mbb.product.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.product.biz.data.CategoryCreateData;
import com.mbb.product.biz.data.CategoryData;
import com.mbb.product.biz.data.CategoryQuery;
import com.mbb.product.biz.data.CategoryUpdateData;
import com.mbb.product.biz.model.CategoryModel;
import com.mbb.product.biz.service.CategoryService;
import com.mbb.product.rest.data.CategoryDeleteData;
import com.mbb.product.rest.data.PriceDeleteData;
import com.mbb.product.rest.data.SubCategoryCreateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        return ResponseEntity.ok(categoryService.getCategories(categoryQuery));
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
        CategoryModel category = new CategoryModel();
        Long id = idService.genId();
        category.setCode(String.valueOf(id));
        category.setName(data.getName());
        category.setId(id);
        categoryService.createCategory(category);
        CategoryData resp = new CategoryData();
        resp.setCode(category.getCode());
        resp.setId(category.getId());
        resp.setName(category.getName());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/create/sub")
    public ResponseEntity createSub(@RequestBody SubCategoryCreateData data) {
        CategoryModel category = new CategoryModel();
        Long id = idService.genId();
        category.setCode(String.valueOf(id));
        category.setName(data.getName());
        category.setId(id);
        category.setParentId(data.getParentId());
        categoryService.createCategory(category);
        CategoryData resp = new CategoryData();
        resp.setCode(category.getCode());
        resp.setId(category.getId());
        resp.setName(category.getName());
        return ResponseEntity.ok(resp);
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
}
