package com.sliit.mtit.SubCategoryService.service;

import com.sliit.mtit.SubCategoryService.dto.GeneralResponse;
import com.sliit.mtit.SubCategoryService.dto.SubCategoryRequest;
import com.sliit.mtit.SubCategoryService.dto.SubCategoryResponse;
import com.sliit.mtit.SubCategoryService.entity.SubCategory;
import com.sliit.mtit.SubCategoryService.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    public GeneralResponse<SubCategoryResponse> saveSubCategory(SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = new SubCategory(subCategoryRequest.getCategoryId());

        if (!checkCategoryExists(subCategoryRequest.getCategoryId()))
            return new GeneralResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Category does not found for provided id",
                null
            );

        subCategory.setName(subCategoryRequest.getName());
        subCategory.setCreatedBy(subCategoryRequest.getCreatedBy());
        subCategory.setCreatedDate(new Date());

        try {
            SubCategory saved = subCategoryRepository.save(subCategory);

            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "Successfully saved sub category",
                new SubCategoryResponse(
                    saved.getId(),
                    saved.getName(),
                    saved.getCreatedBy(),
                    saved.getCreatedDate(),
                    saved.getCategoryId()
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

    public GeneralResponse<List<SubCategoryResponse>> getAllSubCategory() {
        List<SubCategory> categories = subCategoryRepository.findAll();

        if (categories == null)
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No sub categories found",
                Arrays.asList()
            );

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            categories.stream().map(cat ->
                new SubCategoryResponse(
                    cat.getId(),
                    cat.getName(),
                    cat.getCreatedBy(),
                    cat.getCreatedDate(),
                    cat.getCategoryId()
                )).collect(Collectors.toList())
        );
    }

    public GeneralResponse<SubCategoryResponse> getSubCategoryById(Long id) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);

        if (subCategory.isEmpty())
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No sub category found",
                null
            );

        return new GeneralResponse<>(
            HttpStatus.OK.value(),
            null,
            new SubCategoryResponse(
                subCategory.get().getId(),
                subCategory.get().getName(),
                subCategory.get().getCreatedBy(),
                subCategory.get().getCreatedDate(),
                subCategory.get().getCategoryId()
            )
        );
    }

    public GeneralResponse<SubCategoryResponse> updateSubCategoryById(SubCategoryRequest request) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(request.getId());

        if (subCategory.isEmpty())
            return new GeneralResponse<>(
                    HttpStatus.OK.value(),
                    "No sub category found",
                    null
            );

        if (!checkCategoryExists(request.getCategoryId()))
            return new GeneralResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Category does not found for provided category id",
                    null
            );

        if (request.getName() != null)
            subCategory.get().setName(request.getName());
        if (request.getCreatedBy() != null)
            subCategory.get().setCreatedBy(request.getCreatedBy());
        if (request.getCategoryId() != null)
            subCategory.get().setCategoryId(request.getCategoryId());

        try {
            subCategoryRepository.save(subCategory.get());
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                null,
                new SubCategoryResponse(
                    subCategory.get().getId(),
                    subCategory.get().getName(),
                    subCategory.get().getCreatedBy(),
                    subCategory.get().getCreatedDate(),
                    subCategory.get().getCategoryId()
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

    public GeneralResponse<SubCategoryResponse> deleteSubCategoryById(Long id) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);

        if (subCategory.isEmpty())
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                "No sub category found",
                null
            );

        try {
            subCategoryRepository.delete(subCategory.get());
            return new GeneralResponse<>(
                HttpStatus.OK.value(),
                null,
                new SubCategoryResponse(
                    subCategory.get().getId(),
                    subCategory.get().getName(),
                    subCategory.get().getCreatedBy(),
                    subCategory.get().getCreatedDate(),
                    subCategory.get().getCategoryId()
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

    private boolean checkCategoryExists(Long categoryId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        try {
            var response = restTemplate.exchange(
                    "http://localhost:8081/categories/"+categoryId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Object.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            return false;
        }


    }

    public boolean checkAuthorization(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("accessToken", accessToken);

        var response = restTemplate.exchange(
                "http://localhost:8083/api/v1/auth",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Object.class
        );
        return response.getStatusCode() == HttpStatus.OK;
    }

}
