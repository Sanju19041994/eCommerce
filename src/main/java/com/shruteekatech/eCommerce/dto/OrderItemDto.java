package com.shruteekatech.eCommerce.dto;

public class OrderItemDto {

    private Long orderItemId;

    private ProductDto product;

    private Integer quantity;

    private Double totalProductPrice;

    public OrderItemDto() {
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
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

    public OrderItemDto(Long orderItemId, ProductDto product, Integer quantity, Double totalProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.totalProductPrice = totalProductPrice;
    }
}
