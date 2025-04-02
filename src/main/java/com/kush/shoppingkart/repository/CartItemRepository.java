package com.kush.shoppingkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kush.shoppingkart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	void deleteAllByCartId(Long id);

}
