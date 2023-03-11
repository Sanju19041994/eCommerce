package com.shruteekatech.eCommerce.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @OneToOne
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "TOTAL_PRODUCT_PRICE")
    private Double totalProductPrice;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;


    @CreationTimestamp
    @Column(name = "CREATED_DATE",updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE",insertable = false)
    private LocalDate updatedDate;

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, Product product, Integer quantity, Double totalProductPrice, Order order, LocalDate createdDate, LocalDate updatedDate) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.totalProductPrice = totalProductPrice;
        this.order = order;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(Double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}
