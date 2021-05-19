package com.sliit.mtit.CategoryService.controller;

import com.sliit.mtit.CategoryService.dto.CategoryRequest;
import com.sliit.mtit.CategoryService.dto.CategoryResponse;
import com.sliit.mtit.CategoryService.dto.GeneralResponse;
import com.sliit.mtit.CategoryService.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GeneralResponse<CategoryResponse>> addCategory(@RequestBody CategoryRequest categoryRequest) {
        var result = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<GeneralResponse<List<CategoryResponse>>> getAllCategory() {
        GeneralResponse<List<CategoryResponse>> result = categoryService.getAllCategory();
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<CategoryResponse>> getCategory(@PathVariable Long id) {
        GeneralResponse<CategoryResponse> result = categoryService.getCategoryById(id);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @PutMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<CategoryResponse>> updateCategory(
            @PathVariable  Long id, @RequestBody CategoryRequest request) {
        request.setCategoryId(id);
        GeneralResponse<CategoryResponse> result = categoryService.updateCategoryById(request);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @DeleteMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<CategoryResponse>> deleteCategory(@PathVariable Long id) {
        GeneralResponse<CategoryResponse> result = categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }
}