package com.ecomm.client;

import com.ecomm.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-services", url = "http://localhost:8082") // The service name registered in Eureka
public interface ProductClient {

    @GetMapping("/api/products/{productId}")
    ResponseEntity<Product> getProductById(@PathVariable Long productId);
}