package com.ecomm.controllers;

import com.ecomm.exception.ProductNotFoundException;
import com.ecomm.model.Product;
import com.ecomm.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductServiceController {
    //ToDO- Test cases
    //Constructor injection
    private final ProductService productService;

    private ProductServiceController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) Long productId, @RequestParam(required = false) String category){
        if(productId!=null){
            return ResponseEntity.ok().body(productService.getProductById(productId));
        }else if(category!=null){
            List<Product> products=productService.getProductByCategory(category);
            if(!products.isEmpty()){
                return ResponseEntity.ok().body(products);
            }else{
                throw new ProductNotFoundException("No product found with category: "+ category);
            }
        }else{
            return ResponseEntity.ok().body(productService.getAllProducts());
        }
    }
}
