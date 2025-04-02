package com.kush.shoppingkart.Service.Implementation;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.kush.shoppingkart.Service.CartService;
import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import com.kush.shoppingkart.model.Cart;
import com.kush.shoppingkart.model.CartItem;
import com.kush.shoppingkart.repository.CartItemRepository;
import com.kush.shoppingkart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService{

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	
	@Override
	public Cart getCart(Long Id) {
		Cart cart = cartRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not Found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long Id) {
		Cart cart = getCart(Id);
		cartItemRepository.deleteAllByCartId(Id);
		cart.getItems().clear();
		cartRepository.deleteById(Id);
		
	}

	@Override
	public BigDecimal getTotalPrice(Long Id) {
		Cart cart = getCart(Id);
		return cart.getTotalAmount();
	}

}
