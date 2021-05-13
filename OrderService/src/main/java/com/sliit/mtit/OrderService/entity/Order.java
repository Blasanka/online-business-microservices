package com.sliit.mtit.OrderService.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "abc_orders")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String status;
    @Column(name = "user_id")
    private Long userId;
    @Column(name="product_id")
    private Long productId;

    public Order() {
    }

    public Order(Long id, String status) {
        this.id = id;
        this.date = new Date().toString();
        this.status = status;
    }

    public Order(Long userId, Long productId, String status) {
        this.userId = userId;
        this.productId = productId;
        this.status = status;
        this.date = new Date().toString();
    }

    public Order(Long id, String date, String status, Long userId, Long productId) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.userId = userId;
        this.productId = productId;
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
}
