package com.sliit.mtit.OrderService.dto;

public class OrderResponse {

    private Long id;
    private String date;
    private String status;
    private Long userId;
    private Long productId;
    private String username;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String date, String status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public OrderResponse(Long id, String date, String status, Long userId, Long productId) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.userId = userId;
        this.productId = productId;
    }

    public OrderResponse(Long id, String date, String status, Long userId, Long productId, String username) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.userId = userId;
        this.productId = productId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
