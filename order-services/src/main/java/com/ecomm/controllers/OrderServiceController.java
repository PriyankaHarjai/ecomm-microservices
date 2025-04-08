package com.ecomm.controllers;

import com.ecomm.exception.BadRequestException;
import com.ecomm.model.Order;

import com.ecomm.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderServiceController {

    private final OrderService orderService;

    private OrderServiceController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Order> checkout( @PathVariable Long userId){
        if(userId==null){
            //TODO-authenticate user
            throw new BadRequestException("UserId is null");
        }
        return ResponseEntity.ok(orderService.placeOrder(userId));

    }
    @GetMapping("/userId/orderId")
    public ResponseEntity<Order> getOrderStatus(@PathVariable Long userId, @PathVariable Long orderId){
        if(userId==null){
            throw new BadRequestException("UserId is null");
        }
        return ResponseEntity.ok(orderService.getOrderStatus(orderId));
    }
}
