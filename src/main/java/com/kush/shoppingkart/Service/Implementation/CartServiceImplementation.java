package com.kush.shoppingkart.Service.Implementation;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.transaction.Transactional;
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
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}


	@Transactional
	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);

	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		Long newCartId = cartIdGenerator.incrementAndGet();
		newCart.setId(newCartId);
		return cartRepository.save(newCart).getId();

	}
}
