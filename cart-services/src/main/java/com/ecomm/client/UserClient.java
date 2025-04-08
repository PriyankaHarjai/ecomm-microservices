package com.ecomm.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "User-services") // The service name registered in Eureka
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    List<String> getUserDetails(@PathVariable("userId") String userId);
}
