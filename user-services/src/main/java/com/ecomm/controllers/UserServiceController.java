package com.ecomm.controllers;

import com.ecomm.entityBuilders.UserDetailsBuilder;
import com.ecomm.model.UserEntity;
import com.ecomm.model.UserRequestDTO;
import com.ecomm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserServiceController {

    @Autowired
    private UserDetailsBuilder builder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO user) {
        UserEntity userEntity = builder.createUserDTO(user);
        try {
            userService.registerUser(userEntity);
            return ResponseEntity.ok("Registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }
}
