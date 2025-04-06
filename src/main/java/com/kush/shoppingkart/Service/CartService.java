package com.kush.shoppingkart.Service;

import java.math.BigDecimal;

import com.kush.shoppingkart.model.Cart;

public interface CartService {
	
	Cart getCart(Long Id);
	void clearCart(Long Id);
	BigDecimal getTotalPrice(Long Id);

	Long initializeNewCart();
}
