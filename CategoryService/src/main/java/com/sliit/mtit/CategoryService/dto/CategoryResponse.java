package com.sliit.mtit.CategoryService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private Long createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id) {
        this.categoryId = id;
    }

    public CategoryResponse(Long categoryId, String categoryName, Long createdBy, Date createdDate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long id) {
        this.categoryId = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
