package com.kush.shoppingkart.Service;

import com.kush.shoppingkart.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
