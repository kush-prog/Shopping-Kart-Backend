package com.kush.shoppingkart.Service;

import com.kush.shoppingkart.dtos.OrderDto;
import com.kush.shoppingkart.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
