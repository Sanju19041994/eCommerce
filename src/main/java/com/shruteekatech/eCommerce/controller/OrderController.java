package com.shruteekatech.eCommerce.controller;

import com.shruteekatech.eCommerce.constants.AppConstants;
import com.shruteekatech.eCommerce.utills.ApiResponse;
import com.shruteekatech.eCommerce.dto.OrderDto;
import com.shruteekatech.eCommerce.service.OrderService;
import com.shruteekatech.eCommerce.utills.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for create a new order
     * @param orderRequest
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<OrderDto> createNewOrder(@RequestBody OrderRequest orderRequest){
        logger.info("Start : createNewOrder() started from OrderController");
        String userName = "skc@gmail.com";
        OrderDto order = this.orderService.createOrder(orderRequest, userName);
        logger.info("Complete : createNewOrder() completed from OrderController");
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for update order by using orderId & orderDto
     * @param orderDto
     * @param orderId
     * @return
     * @exception : ResourceNotFoundException
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder
            (@Valid @RequestBody OrderDto orderDto, @PathVariable Long orderId)
    {
        logger.info("Start : updateOrder() started from OrderController");
        OrderDto updated = this.orderService.updateOrder(orderDto, orderId);
        logger.info("Complete : updateOrder() completed from OrderController");
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for find single order by orderId
     * @param orderId
     * @return
     * @exception : ResourceNotFoundException
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> singleOrderById(@PathVariable Long orderId){
        logger.info("Start : singleOrderById() started from OrderController");
        OrderDto orderByOrderId = this.orderService.getOrderByOrderId(orderId);
        logger.info("Complete : singleOrderById() completed from OrderController");
        return new ResponseEntity<>(orderByOrderId,HttpStatus.OK);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for get list of orders
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> allOrders(){
        logger.info("Start : allOrders() started from OrderController");
        List<OrderDto> allOrders = this.orderService.getAllOrders();
        logger.info("Complete : allOrders() completed from OrderController");
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    /**
     * @autor Sanju
     * @since 1.0
     * @apiNote : api created for delete order by orderId
     * @param orderId
     * @return
     * @exception : ResourceNotFoundException
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Long orderId){
        logger.info("Start : deleteOrderById() started from OrderController");
       this.orderService.deleteOrder(orderId);
        logger.info("Complete : deleteOrderById() completed from OrderController");
        return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE_SUCCESS,true), HttpStatus.OK);
    }

}
