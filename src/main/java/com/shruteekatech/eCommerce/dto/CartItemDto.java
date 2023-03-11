package com.shruteekatech.eCommerce.dto;

public class CartItemDto {

    private Long cartItemId;

    private ProductDto product;

    private Integer quantity;

    private Double totalProductPrice;

 // we have created CartItem from Cart itself that's why no need to take cartDto again here


    public CartItemDto(Long cartItemId, ProductDto product, Integer quantity, Double totalProductPrice) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
        this.totalProductPrice = totalProductPrice;
    }

    public CartItemDto() {
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
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

    @Override
    public String toString() {
        return "CartItemDto{" +
                "cartItemId=" + cartItemId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalProductPrice=" + totalProductPrice +
                '}';
    }
}
