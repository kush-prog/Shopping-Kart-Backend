package com.kush.shoppingkart.Service;

import com.kush.shoppingkart.model.Order;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

}
