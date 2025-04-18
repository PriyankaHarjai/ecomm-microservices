package com.ecomm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long productId;

    private String name;
    private String description;
    private String category;
    private Double amount;
    private boolean inStock;
    private int quantity;
    private Long sellerId;
}
