package com.sliit.mtit.PaymentService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentReference;
    private String paymentMadeAt;
    private Double totalPrice;
    private Long userId;
    private Long orderId;

    public Payment() {
    }

    public Payment(String paymentReference, String paymentMadeAt, Long userId, Long orderId, Double totalPrice) {
        this.paymentReference = paymentReference;
        this.paymentMadeAt = paymentMadeAt;
        this.userId = userId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public Payment(Long id, String paymentReference, String paymentMadeAt, Long userId, Long orderId, Double totalPrice) {
        this.id = id;
        this.paymentReference = paymentReference;
        this.paymentMadeAt = paymentMadeAt;
        this.userId = userId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentMadeAt() {
        return paymentMadeAt;
    }

    public void setPaymentMadeAt(String paymentMadeAt) {
        this.paymentMadeAt = paymentMadeAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
