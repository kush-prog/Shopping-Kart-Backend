package com.kush.shoppingkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kush.shoppingkart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Cart findByUserId(Long userId);
}
