package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.dto.CartDto;
import com.shruteekatech.eCommerce.entity.Cart;
import com.shruteekatech.eCommerce.service.CartService;
import com.shruteekatech.eCommerce.utills.ItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    /**
     * @apiNote : * this api created for create a new cart
                  * this api also work for quantity update
     * @exception : ResourceNotFoundException
     * @param item
     * @return : CartDto
     */
    @PostMapping("/addToCart")
    ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest item){
        logger.info("Start : addToCart() started from CartController");
        // Providing userName statically from DB, But actually which user login he can access this
        // login user's userName came here after authentication dynamically
        String userName = "skc@gmail.com";
        CartDto cartDto = this.cartService.addItemToCart(item, userName);
        logger.info("Complete : addToCart() completed from CartController");
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    /**
     *
     * @param userName
     * @return
     */
    @GetMapping("/userName/{userName}")
    ResponseEntity<CartDto> getCartItem(@PathVariable String userName){
        logger.info("Start : getCartItem() started from CartController");
        CartDto dto = this.cartService.getCart(userName);
        logger.info("Complete : getCartItem() completed from CartController");
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/byCartId/{cartId}")
    ResponseEntity<CartDto> getCartItemById(@PathVariable Long cartId){
        logger.info("Start : getCartItemById() started from CartController");
        CartDto cartById = this.cartService.getCartById(cartId);
        logger.info("Complete : getCartItemById() completed from CartController");
        return new ResponseEntity<>(cartById,HttpStatus.OK);
    }

    @PutMapping("/byId/{cartItemId}")
    ResponseEntity<CartDto> removeCartItem(@PathVariable int cartItemId){
        logger.info("Start : removeCartItem() started from CartController");
        String userName = "skc@gmail.com";
        CartDto cartDto = this.cartService.removeItem(userName, cartItemId);
        logger.info("Complete : removeCartItem() completed from CartController");
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @PutMapping("/byProductId/{productId}")
    ResponseEntity<CartDto> removeCartItemByProductId(@PathVariable Long productId){
        logger.info("Start : removeCartItemByProductId() started from CartController");
        String userName = "skc@gmail.com";
        CartDto cartDto = this.cartService.removeItemByProductId(userName, productId);
        logger.info("Complete : removeCartItemByProductId() completed from CartController");
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }


}
