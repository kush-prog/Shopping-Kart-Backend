package com.kush.shoppingkart.Service.Implementation;

import com.kush.shoppingkart.Service.CartItemService;
import com.kush.shoppingkart.Service.CartService;
import com.kush.shoppingkart.Service.ProductService;
import com.kush.shoppingkart.exceptions.ResourceNotFoundException;
import com.kush.shoppingkart.model.Cart;
import com.kush.shoppingkart.model.CartItem;
import com.kush.shoppingkart.model.Product;
import com.kush.shoppingkart.repository.CartItemRepository;
import com.kush.shoppingkart.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final ProductService productService;
	private final CartService cartService;

	@Override
	@Transactional
	public void addItemToCart(Long cartId, Long productId, int quantity) {
	    Cart cart = cartService.getCart(cartId); // Make sure this uses cartRepository.findById()
	    Product product = productService.getProductById(productId);

	    CartItem cartItem = cart.getItems().stream()
	        .filter(item -> item.getProduct().getId().equals(productId))
	        .findFirst()
	        .orElse(null);

	    if (cartItem == null) {
	        cartItem = new CartItem();
	        cartItem.setCart(cart);
	        cartItem.setProduct(product);
	        cartItem.setQuantity(quantity);
	        cartItem.setUnitPrice(product.getPrice());
	        cartItem.setTotalPrice();
	        cart.addItem(cartItem); // Should add to cart.getItems()
	    } else {
	        cartItem.setQuantity(cartItem.getQuantity() + quantity);
	        cartItem.setTotalPrice(); // update total price based on new quantity
	    }

	    // Only save the cart (if cascading is properly configured)
	    cartRepository.save(cart);
	}


    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
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
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long itemId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
    }

}
