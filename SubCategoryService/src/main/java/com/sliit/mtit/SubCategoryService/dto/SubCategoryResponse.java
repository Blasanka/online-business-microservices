package com.sliit.mtit.SubCategoryService.dto;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class SubCategoryResponse {

    private Long id;
    private String name;
    private Long createdBy;
    private Date createdDate;
    private Long categoryId;

    public SubCategoryResponse() {
    }

    public SubCategoryResponse(Long id) {
        this.categoryId = id;
    }

    public SubCategoryResponse(Long id, String name, Long createdBy, Date createdDate, Long categoryId) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
