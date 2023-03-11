package com.shruteekatech.eCommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OrderDto {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String orderStatus;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String paymentStatus;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String billingAddress;

    @NotNull
    private Double totalAmount;

    private LocalDate orderCreated;

    private LocalDate orderDelivered;

    private UserDto user;

    private Set<OrderItemDto> orderItems = new HashSet<>();

    public OrderDto() {
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderCreated() {
        return orderCreated;
    }

    public void setOrderCreated(LocalDate orderCreated) {
        this.orderCreated = orderCreated;
    }

    public LocalDate getOrderDelivered() {
        return orderDelivered;
    }

    public void setOrderDelivered(LocalDate orderDelivered) {
        this.orderDelivered = orderDelivered;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}