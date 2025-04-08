package com.ecomm.services;

import com.ecomm.client.CartServiceClient;
import com.ecomm.exception.BadRequestException;
import com.ecomm.exception.InvalidOrderException;
import com.ecomm.model.Cart;
import com.ecomm.exception.NoRecordsException;
import com.ecomm.model.Order;
import com.ecomm.repository.OrderRepositoty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CartServiceClient cartServiceClient;

    private final OrderRepositoty orderRepositoty;

    private OrderService(CartServiceClient cartServiceClient, OrderRepositoty orderRepositoty){
        this.cartServiceClient=cartServiceClient;
        this.orderRepositoty=orderRepositoty;
    }

    public Order placeOrder(Long userId) {
        ResponseEntity<Cart> responseEntity = cartServiceClient.fetchCart(userId);
        if(!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody() || responseEntity.getBody()==null){
            throw new BadRequestException("Error fetching details from cart client");
        }

        Cart cart =responseEntity.getBody();
        Order order = createOrder(cart, userId);
        return orderRepositoty.save(order);

    }

    private Order createOrder(Cart cart, Long userId) {
        Order order=new Order();
        order.setStatus("PENDING");
        order.setOrderId(null);
        order.setPrice(cart.getTotalAmount());
        order.setUserId(userId);
        order.setItemList(cart.getItemList());
        return order;
    }

    public Order getOrderStatus(Long orderId) {
        Order order = orderRepositoty.findById(orderId)
                .orElseThrow(()-> new InvalidOrderException("Invalid order id: "+orderId));
        if(order==null){
            throw new NoRecordsException("No records found");
        }
        return order;
    }
}
