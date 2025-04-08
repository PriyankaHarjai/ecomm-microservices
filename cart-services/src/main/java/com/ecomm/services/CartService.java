package com.ecomm.services;

import com.ecomm.client.ProductClient;
import com.ecomm.exception.BadRequestException;
import com.ecomm.exception.OutOfStockException;
import com.ecomm.exception.ProductNotFoundException;
import com.ecomm.model.Cart;
import com.ecomm.model.CartItem;
import com.ecomm.model.Product;
import com.ecomm.repositories.CartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    private final ProductClient productClient;
    private final CartRepository cartRepository;

    private CartService(ProductClient productClient, CartRepository cartRepository) {
        this.productClient = productClient;
        this.cartRepository = cartRepository;
    }

    public String addProductToCart(Long userId, Long productId, int quantity) {
        ResponseEntity<Product> productResponseEntity = productClient.getProductById(productId);

        if (!productResponseEntity.getStatusCode().is2xxSuccessful() && !productResponseEntity.hasBody()) {
            throw new ProductNotFoundException("Product Not found");
        }

        Product product = productResponseEntity.getBody();
        if (product == null) {
            throw new ProductNotFoundException("Product Not found with id: " + productId);
        }

        if (quantity > product.getQuantity()) {
            throw new OutOfStockException("Out of stock product");
        }
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = new CartItem(null, productId, product.getAmount(), quantity);
        cart.getItemList().add(cartItem);
        cart.setTotalAmount(quantity*product.getAmount());

        cartRepository.save(cart);
        return "Item has been added successfully.";

    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findById(userId)
                .orElseGet(() -> new Cart(userId, new ArrayList<>(), 0d));
    }

    public Cart findByUserId(Long userId) {
        return getOrCreateCart(userId);
    }

    public void removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        if (!cart.getItemList().isEmpty()) {
            cart.getItemList().removeIf(item -> item.getProductId().equals(productId));
        } else {
            throw new BadRequestException("There is no product to be deleted.");
        }
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        if (cart.getItemList().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        cartRepository.deleteById(userId);
    }

    public void updateCartItem(Long userId, Long productId, int quantity) {

        Cart cart = getOrCreateCart(userId);
        if (cart.getItemList().isEmpty()) {
            throw new BadRequestException("There is no product to be deleted.");
        }
        cart.getItemList().stream()
                .filter(cartItem -> cartItem.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItem -> cartItem.setQuantity(quantity));
        cartRepository.save(cart);
    }
}
