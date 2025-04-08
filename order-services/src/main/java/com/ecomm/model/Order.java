package com.ecomm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Order {

    private Long orderId;
    private Long userId;
    private List<CartItem> itemList;
    private double price;
    private String status; // e.g., PENDING, COMPLETED, CANCELLED
}
