package com.shruteekatech.eCommerce.serviceImpl;

import com.shruteekatech.eCommerce.dto.OrderDto;
import com.shruteekatech.eCommerce.entity.*;
import com.shruteekatech.eCommerce.exception.ResourceNotFoundException;
import com.shruteekatech.eCommerce.repository.CartRepo;
import com.shruteekatech.eCommerce.repository.OrderRepo;
import com.shruteekatech.eCommerce.repository.UserRepo;
import com.shruteekatech.eCommerce.service.OrderService;
import com.shruteekatech.eCommerce.utills.OrderRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public OrderDto createOrder(OrderRequest orderRequest, String userName) {
        logger.info("Start : createOrder() started from OrderServiceImpl");
        // find the user
        User user = this.userRepo.findByEmail(userName).orElseThrow(() -> new ResourceNotFoundException("User", "User Name or Email"));
        // take required details from orderRequest
        String address = orderRequest.getAddress();
        Long cartId = orderRequest.getCartId();

        // now find the cart by cartId which is coming from OrderRequest
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart Id"));

        // take all items from cart
        Set<CartItem> items = cart.getItems();

        // create a order object & set all order related details
        Order order = new Order();
        // calculate totalOrderAmount into orderItem for this declare a variable in AtomicReference
        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);
        // cart items converted into orderItem (means taking orderItems from cartItems)
        Set<OrderItem> orderItems = items.stream().map((cartItem) ->
                                     {
                                         OrderItem orderItem = new OrderItem();
                                         orderItem.setProduct(cartItem.getProduct());
                                         orderItem.setQuantity(cartItem.getQuantity());
                                         orderItem.setTotalProductPrice(cartItem.getTotalProductPrice());
                                         orderItem.setOrder(order);

                                         totalOrderPrice.set(totalOrderPrice.get()+ orderItem.getTotalProductPrice());
                                         return orderItem;
                                     }).collect(Collectors.toSet());

        // Set all order related details into Order Object
        order.setOrderItems(orderItems);
        order.setBillingAddress(address);
        order.setTotalAmount(totalOrderPrice.get());
        order.setOrderStatus("Created");
        order.setPaymentStatus("Not Paid");
        order.setOrderCreated(LocalDate.now());
        order.setOrderDelivered(null);
        order.setUser(user);

        Order savedOrder = this.orderRepo.save(order);

        // if order placed then remove items from cart & update cart
        cart.getItems().clear();
        this.cartRepo.save(cart);

        logger.info("Complete : createOrder() completed from OrderServiceImpl");
        return this.modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId) {
        logger.info("Start : updateOrder() started from OrderServiceImpl");
        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id"));

        order.setOrderStatus(orderDto.getOrderStatus());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setOrderDelivered(orderDto.getOrderDelivered());

        Order updated = this.orderRepo.save(order);
        logger.info("Complete : updateOrder() completed from OrderServiceImpl");
        return this.modelMapper.map(updated, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(Long orderId) {
        logger.info("Start : getOrderByOrderId() started from OrderServiceImpl");
        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id"));
        logger.info("Complete : getOrderByOrderId() completed from OrderServiceImpl");
        return this.modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        logger.info("Start : getAllOrders() started from OrderServiceImpl");
        List<Order> all = this.orderRepo.findAll();
        List<OrderDto> allOrders = all.stream().map((orders) -> this.modelMapper.
                                  map(orders, OrderDto.class)).collect(Collectors.toList());
        logger.info("Complete : getAllOrders() completed from OrderServiceImpl");
        return allOrders;
    }

    @Override
    public void deleteOrder(Long orderId) {
        logger.info("Start : deleteOrder() started from OrderServiceImpl");
        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Order Id"));
        this.orderRepo.delete(order);
        logger.info("Complete : deleteOrder() completed from OrderServiceImpl");

    }
}
