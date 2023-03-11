package com.shruteekatech.eCommerce.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "ORDER_DETAILS")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "BILLING_ADDRESS")
    private String billingAddress;

    @Column(name = "ORDER_AMOUNT")
    private Double totalAmount;

    @CreationTimestamp
    @Column(name = "ORDER_CREATED",updatable = false)
    private LocalDate orderCreated;

    @Column(name = "ORDER_DELIVERED")
    private LocalDate orderDelivered;

    @UpdateTimestamp
    @Column(name = "ORDER_UPDATED",insertable = false)
    private LocalDate orderUpdate;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order(Long orderId, String orderStatus, String paymentStatus, String billingAddress, Double totalAmount, LocalDate orderCreated, LocalDate orderDelivered, LocalDate orderUpdate, User user, Set<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.billingAddress = billingAddress;
        this.totalAmount = totalAmount;
        this.orderCreated = orderCreated;
        this.orderDelivered = orderDelivered;
        this.orderUpdate = orderUpdate;
        this.user = user;
        this.orderItems = orderItems;


    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public LocalDate getOrderUpdate() {
        return orderUpdate;
    }

    public void setOrderUpdate(LocalDate orderUpdate) {
        this.orderUpdate = orderUpdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}
