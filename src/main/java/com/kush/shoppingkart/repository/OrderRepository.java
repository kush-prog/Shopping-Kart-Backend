package com.kush.shoppingkart.repository;

import com.kush.shoppingkart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
