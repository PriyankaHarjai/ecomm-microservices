package com.ecomm.repository;

import com.ecomm.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface OrderRepositoty extends JpaRepository<Order, Long> {
}
