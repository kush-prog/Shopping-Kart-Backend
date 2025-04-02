package com.kush.shoppingkart.Service;

public interface CartItemService {
	
	void addItemToCart(Long cartId, Long productId, int quantity);
	void removeItemFromCart(Long cartId, Long productId);
	void updateItemQuantity(Long cartId, Long productId, int quantity);
	
}
