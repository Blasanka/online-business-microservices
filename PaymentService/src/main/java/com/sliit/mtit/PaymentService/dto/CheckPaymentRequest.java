package com.sliit.mtit.PaymentService.dto;

public class CheckPaymentRequest {

    private Long userId;
    private Long orderId;
    private String paymentReference;

    public CheckPaymentRequest() {
    }

    public CheckPaymentRequest(Long userId, Long orderId, String paymentReference) {
        this.userId = userId;
        this.orderId = orderId;
        this.paymentReference = paymentReference;
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

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
