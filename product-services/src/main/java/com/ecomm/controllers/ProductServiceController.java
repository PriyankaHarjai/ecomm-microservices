package com.ecomm.controllers;

import com.ecomm.exception.BadRequestException;
import com.ecomm.exception.ProductNotFoundException;
import com.ecomm.model.Product;
import com.ecomm.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductServiceController {
    //ToDO- Test cases
    //Constructor injection
    private final ProductService productService;

    private ProductServiceController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        if (productId != null) {
            return ResponseEntity.ok().body(productService.getProductById(productId));
        } else {
            throw new BadRequestException("Mandatory Params are missing");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsOrByCategory(@RequestParam(required = false) String category) {
        if (category != null) {
            List<Product> products = productService.getProductByCategory(category);
            if (!products.isEmpty()) {
                return ResponseEntity.ok().body(products);
            } else {
                throw new ProductNotFoundException("No product found with category: " + category);
            }
        } else {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                throw new ProductNotFoundException("No product found");
            }
            return ResponseEntity.ok().body(products);
        }

    }
}
