package com.shruteekatech.eCommerce.service;

import com.shruteekatech.eCommerce.dto.CartDto;
import com.shruteekatech.eCommerce.entity.Cart;
import com.shruteekatech.eCommerce.entity.User;
import com.shruteekatech.eCommerce.utills.ItemRequest;

import java.util.Optional;

public interface CartService {

    // add items to cart,
    // First we will check cart availability , if cart available then we will add items into cart
    // otherwise need to create a new cart and item will be added on it

    public CartDto addItemToCart(ItemRequest item, String userName);

    // get Cart of User
    public CartDto getCart(String userName);

    // get Cart by cartId
    public CartDto getCartById(Long cartId);

    // remove items from cart
    public CartDto removeItem(String userName, int cartItemId);

    public CartDto removeItemByProductId(String userName, Long productId);



}
