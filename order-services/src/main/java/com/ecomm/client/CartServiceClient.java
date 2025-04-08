package com.ecomm.client;

import com.ecomm.model.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", url = "http://localhost:8083")
public interface CartServiceClient {

    @GetMapping("/{userId}")
    ResponseEntity<Cart> fetchCart(@PathVariable Long userId);
}
