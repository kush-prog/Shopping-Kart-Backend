package com.kush.shoppingkart.Service.Implementation;

import com.kush.shoppingkart.Service.CartService;
import com.kush.shoppingkart.Service.ProductService;
import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import com.kush.shoppingkart.model.Cart;
import com.kush.shoppingkart.model.CartItem;
import com.kush.shoppingkart.model.Product;
import com.kush.shoppingkart.repository.CartRepository;
import org.springframework.stereotype.Service;

import com.kush.shoppingkart.Service.CartItemService;
import com.kush.shoppingkart.repository.CartItemRepository;
import com.kush.shoppingkart.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final ProductService productService;
	private final CartService cartService;

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		CartItem cartItem = cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(new CartItem());

		if(cartItem.getId() == null){
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		} else{
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);

	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		CartItem itemToRemove = cart.getItems().
				stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst()
				.ifPresent(item -> {
					item.setQuantity(quantity);
					item.setUnitPrice(item.getProduct().getPrice());
					item.setTotalPrice();
				});
	}

}
