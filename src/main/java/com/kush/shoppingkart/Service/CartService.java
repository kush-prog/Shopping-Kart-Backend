package com.kush.shoppingkart.Service;

import java.math.BigDecimal;

import com.kush.shoppingkart.model.Cart;
import com.kush.shoppingkart.model.User;

public interface CartService {
	
	Cart getCart(Long Id);
	void clearCart(Long Id);
	BigDecimal getTotalPrice(Long Id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
