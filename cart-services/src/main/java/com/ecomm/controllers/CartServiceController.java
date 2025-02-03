package com.ecomm.controllers;

import com.ecomm.exception.BadRequestException;
import com.ecomm.model.Cart;
import com.ecomm.repositories.CartRepository;
import com.ecomm.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartServiceController {

    private final CartService cartService;
    private final CartRepository cartRepository;

    private CartServiceController(CartService cartService, CartRepository cartRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    @PostMapping("/items/{userId}/{productId}/{quantity}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId, quantity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> fetchCart(@PathVariable Long userId) {
        if (userId == null) {
            throw new BadRequestException("UserId missing");
        }
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    @DeleteMapping("/items/{userId}/{productId}")
    public ResponseEntity<String> removeItem(
            @PathVariable("userId") Long userId,
            @PathVariable Long productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok("Product has been removed successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable("userId") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{userId}/{productId}")
    public ResponseEntity<String> updateItem(
            @PathVariable("userId") Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        cartService.updateCartItem(userId, productId, quantity);
        return ResponseEntity.ok("Cart has been updated successfully");
    }
}
