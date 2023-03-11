package com.shruteekatech.eCommerce.utills;

public class OrderRequest {

    private String address;

    private Long cartId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
