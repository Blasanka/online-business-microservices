package com.sliit.mtit.PaymentService.dto;

public class MakePaymentRequest {

    private Long userId;
    private Long orderId;
    private Double orderPrice;
    private Double shippingCharge;

    public MakePaymentRequest() {
    }

    public MakePaymentRequest(Long userId, Long orderId, Double orderPrice, Double shippingCharge) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.shippingCharge = shippingCharge;
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

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }
}
