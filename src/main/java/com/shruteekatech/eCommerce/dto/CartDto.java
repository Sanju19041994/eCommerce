package com.shruteekatech.eCommerce.dto;

import java.util.HashSet;
import java.util.Set;


public class CartDto {

    private Long cartId;

    private UserDto user;

    private Set<CartItemDto> items = new HashSet<>();

    public CartDto(Long cartId, UserDto user, Set<CartItemDto> items) {
        this.cartId = cartId;
        this.user = user;
        this.items = items;
    }

    public CartDto() {
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<CartItemDto> getItems() {
        return items;
    }

    public void setItems(Set<CartItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", items=" + items +
                '}';
    }
}
