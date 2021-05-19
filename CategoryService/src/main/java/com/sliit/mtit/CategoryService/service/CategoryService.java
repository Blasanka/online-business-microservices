package com.sliit.mtit.CategoryService.service;

import com.sliit.mtit.CategoryService.dto.CategoryRequest;
import com.sliit.mtit.CategoryService.dto.CategoryResponse;
import com.sliit.mtit.CategoryService.dto.GeneralResponse;
import com.sliit.mtit.CategoryService.entity.Category;
import com.sliit.mtit.CategoryService.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public GeneralResponse<CategoryResponse> saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest.getCategoryId());
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCreatedBy(categoryRequest.getCreatedBy());
        category.setCreatedDate(new Date());

        try {
            Category saved = categoryRepository.save(category);

            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "Successfully saved category",
                new CategoryResponse(
                    saved.getCategoryId(),
                    saved.getCategoryName(),
                    saved.getCreatedBy(),
                    saved.getCreatedDate()
                )
            );
        } catch (Exception e) {
            return new GeneralResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                null
            );
        }
    }

    public GeneralResponse<List<CategoryResponse>> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        if (categories == null)
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No categories found",
                Arrays.asList()
            );

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            categories.stream().map(cat ->
                new CategoryResponse(
                    cat.getCategoryId(),
                    cat.getCategoryName(),
                    cat.getCreatedBy(),
                    cat.getCreatedDate()
                )).collect(Collectors.toList())
        );
    }

    public GeneralResponse<CategoryResponse> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty())
            return new GeneralResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "No category found",
                null
            );

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            new CategoryResponse(
                category.get().getCategoryId(),
                category.get().getCategoryName(),
                category.get().getCreatedBy(),
                category.get().getCreatedDate()
            )
        );
    }

    public GeneralResponse<CategoryResponse> updateCategoryById(CategoryRequest request) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());

        if (category.isEmpty())
            return new GeneralResponse<>(
                    HttpStatus.OK.value(),
                    "No category found",
                    null
            );

        System.out.println(request.getCategoryName());
        if (request.getCategoryName() != null)
            category.get().setCategoryName(request.getCategoryName());
        if (request.getCreatedBy() != null)
            category.get().setCreatedBy(request.getCreatedBy());

        try {
            categoryRepository.save(category.get());
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                null,
                new CategoryResponse(
                    category.get().getCategoryId(),
                    category.get().getCategoryName(),
                    category.get().getCreatedBy(),
                    category.get().getCreatedDate()
                )
            );
        } catch (Exception e) {
            return new GeneralResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Something went wrong",
                    null
            );
        }
    }

    public GeneralResponse<CategoryResponse> deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty())
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No category found",
                null
            );

        try {
            categoryRepository.delete(category.get());
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                null,
                new CategoryResponse(
                    category.get().getCategoryId(),
                    category.get().getCategoryName(),
                    category.get().getCreatedBy(),
                    category.get().getCreatedDate()
                )
            );
        } catch (Exception e) {
            return new GeneralResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                null
            );
        }
    }
}
