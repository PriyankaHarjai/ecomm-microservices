package com.ecomm.services;

import com.ecomm.exception.InvalidProductException;
import com.ecomm.exception.ProductNotFoundException;
import com.ecomm.model.Product;
import com.ecomm.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public Product addProduct(Product product) {
        if(product!=null){
            if (product.getName() == null || product.getAmount() == null || product.getCategory() == null) {
                throw new InvalidProductException("Missing required fields: name, price, category.");
            }
            return productRepository.save(product);
        }else{
            throw new NullPointerException("Product not found.");
        }
    }

    public void deleteProductById(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            productRepository.deleteById(productId);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }

    public List<Product> getProductByCategory(String category) {
        if(category!=null){
            return productRepository.findByCategory(category);
        }else{
            throw new NullPointerException("Category is null");
        }
    }
}
