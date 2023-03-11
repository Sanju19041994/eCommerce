package com.shruteekatech.eCommerce.service;

import com.shruteekatech.eCommerce.dto.OrderDto;
import com.shruteekatech.eCommerce.utills.OrderRequest;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(OrderRequest orderRequest,String userName);

    public OrderDto updateOrder(OrderDto orderDto,Long orderId);

    public OrderDto getOrderByOrderId(Long orderId);

    public List<OrderDto> getAllOrders();

    public void deleteOrder(Long orderId);


}
