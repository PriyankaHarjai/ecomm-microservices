package com.ecomm.controllers;

import com.ecomm.model.Product;
import com.ecomm.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/csr/products")
public class CsrProductServiceController {
    //ToDO- Test cases
    //Constructor injection
    private final ProductService productService;

    private CsrProductServiceController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product response =  productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product with id "+ productId+" has been deleted successfully.");
    }
}
