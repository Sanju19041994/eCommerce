package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.dto.CartDto;
import com.shruteekatech.eCommerce.dto.UserDto;
import com.shruteekatech.eCommerce.entity.Cart;
import com.shruteekatech.eCommerce.entity.CartItem;
import com.shruteekatech.eCommerce.entity.Product;
import com.shruteekatech.eCommerce.entity.User;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.repository.CartRepo;
import com.shruteekatech.eCommerce.repository.ProductRepo;
import com.shruteekatech.eCommerce.repository.UserRepo;
import com.shruteekatech.eCommerce.service.CartService;
import com.shruteekatech.eCommerce.utills.ItemRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(ItemRequest item, String userName) {
        logger.info("Start : addItemToCart() started from CartServiceImpl");
        // Getting productId & product quantity from ItemRequest
        Long productId = item.getProductId();
        Integer quantity = item.getQuantity();

        if(quantity<=0){
            throw new ResourceNotFoundException("Product","Quantity : Invalid Quantity Input..!!");
        }
        // Getting user object from userName(email)
        User user = this.userRepo.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User", "UserName/Email"));

        // Getting product object from DB
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id"));

        //getting product price with help of stock availability
        if(!product.isStock()){
            throw new ResourceNotFoundException("Product", "Quantity : Out Of Stock");
        }
        product.getProductPrice();

        // creating CartItem (creating new cart)
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotalProductPrice();
        // getting cart from user.
        Cart cart = user.getCart();

        if(cart==null){
            // if cart is null means used doesn't have any cart : creating new cart
            cart = new Cart();
            // set user to cart
            cart.setUser(user);
        }


        // get cartItem from cart
        Set<CartItem> items = cart.getItems();

        // check if cartItem available at cart then quantity will increase of item into cart,
        // if not available then item will be added into cart
        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        //  boolean flag = false; No variable declaration allow inside lambda, that's why AtomicReference taken
        Set<CartItem> newItems = items.stream().map((i) ->
                                   {
                                       if(i.getProduct().getProductId()==product.getProductId()){
                                           i.setQuantity(quantity);
                                           i.setTotalProductPrice();
                                           flag.set(true);
                                       }
                                    return i;
                                   }).collect(Collectors.toSet());

        // check : if any update happen , then newItems will be added into items variable
        if(flag.get()){
            // add all newtItems into cart in place of old cartItems
           items.clear();
           items.addAll(newItems);
        }else{
            cartItem.setCart(cart);
            items.add(cartItem);
        }
        Cart savedCart = this.cartRepo.save(cart);
        logger.info("Complete : addItemToCart() completed from CartServiceImpl");
        return this.modelMapper.map(savedCart,CartDto.class);
    }

    @Override
    public CartDto getCart(String userName) {
        logger.info("Start : getCart() started from CartServiceImpl");
        User byEmail = this.userRepo.findByEmail(userName).orElseThrow(()->new ResourceNotFoundException("User","UserName/Email"));
        Cart cart = this.cartRepo.findByUser(byEmail).orElseThrow(() -> new ResourceNotFoundException("Cart", "UserName"));
        CartDto cartDto = this.modelMapper.map(cart, CartDto.class);
        logger.info("Complete : getCart() completed from CartServiceImpl");
        return cartDto;
    }

    @Override
    public CartDto getCartById(Long cartId) {
        logger.info("Start : getCartById() started from CartServiceImpl");
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart Id"));
        logger.info("Complete : getCartById() completed from CartServiceImpl");
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto removeItem(String userName, int cartItemId) {
        logger.info("Start : removeItem() started from CartServiceImpl");
        // get user by userName
        User user = this.userRepo.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User", "UserName or Email"));
        // get cart from user
        Cart cart = user.getCart();
        // get items from cart
        Set<CartItem> items = cart.getItems();
        // remove item from cartItems with help of cartItemId
        boolean removeIf = items.removeIf((item) -> item.getCartItemId() == cartItemId);
        // now save the cart
        Cart updatedCart = this.cartRepo.save(cart);
        logger.info("Complete : removeItem() completed from CartServiceImpl");
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto removeItemByProductId(String userName, Long productId) {
        logger.info("Start : removeItemByProductId() started from CartServiceImpl");
        User user = this.userRepo.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User", "UserName or Email"));
        Cart cart = user.getCart();
        Set<CartItem> items = cart.getItems();
        boolean removeIf = items.removeIf((item) -> item.getProduct().getProductId() == productId);
        Cart updatedCart = this.cartRepo.save(cart);
        logger.info("Complete : removeItemByProductId() completed from CartServiceImpl");
        return this.modelMapper.map(updatedCart, CartDto.class);
    }


}
