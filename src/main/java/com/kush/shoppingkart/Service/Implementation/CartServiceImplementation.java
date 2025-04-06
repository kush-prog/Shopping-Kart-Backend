package com.kush.shoppingkart.Service.Implementation;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.kush.shoppingkart.Service.CartService;
import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import com.kush.shoppingkart.model.Cart;
import com.kush.shoppingkart.repository.CartItemRepository;
import com.kush.shoppingkart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public Cart getCart(Long id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + id));
	}

	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cart.setTotalAmount(BigDecimal.ZERO);
		cartRepository.save(cart);
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		newCart.setTotalAmount(BigDecimal.ZERO);
		return cartRepository.save(newCart).getId();
	}
}
