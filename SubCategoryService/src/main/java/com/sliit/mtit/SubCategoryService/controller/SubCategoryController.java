package com.sliit.mtit.SubCategoryService.controller;

import com.sliit.mtit.SubCategoryService.dto.SubCategoryRequest;
import com.sliit.mtit.SubCategoryService.dto.SubCategoryResponse;
import com.sliit.mtit.SubCategoryService.dto.GeneralResponse;
import com.sliit.mtit.SubCategoryService.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GeneralResponse<SubCategoryResponse>> addSubCategory(@RequestBody SubCategoryRequest categoryRequest) {
        var result = subCategoryService.saveSubCategory(categoryRequest);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<GeneralResponse<List<SubCategoryResponse>>> getAllSubCategory() {
        GeneralResponse<List<SubCategoryResponse>> result = subCategoryService.getAllSubCategory();
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<SubCategoryResponse>> getSubCategory(@PathVariable Long id) {
        GeneralResponse<SubCategoryResponse> result = subCategoryService.getSubCategoryById(id);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @PutMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<SubCategoryResponse>> updateSubCategory(
            @PathVariable  Long id, @RequestBody SubCategoryRequest request) {
        request.setId(id);
        GeneralResponse<SubCategoryResponse> result = subCategoryService.updateSubCategoryById(request);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }

    @DeleteMapping(produces = "application/json", path = "/{id}")
    public ResponseEntity<GeneralResponse<SubCategoryResponse>> deleteSubCategory(@PathVariable Long id) {
        GeneralResponse<SubCategoryResponse> result = subCategoryService.deleteSubCategoryById(id);
        return new ResponseEntity<>(result, HttpStatus.resolve(result.getStatus()));
    }
}