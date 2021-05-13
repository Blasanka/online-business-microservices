package com.sliit.mtit.OrderService.dto;

import java.util.List;

public class OrderRequest {

    private Long userId;
    private Long productId;
    private String type;

    public OrderRequest() {
    }

    public OrderRequest(Long userId, Long productId, String type) {
        this.userId = userId;
        this.productId = productId;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
