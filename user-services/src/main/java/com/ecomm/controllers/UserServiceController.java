package com.ecomm.controllers;

import com.ecomm.exception.UnAuthorizedException;
import com.ecomm.model.LoginRequest;
import com.ecomm.model.User;
import com.ecomm.model.UserRequestDTO;
import com.ecomm.services.UserService;
import com.ecomm.entityBuilders.UserDetailsBuilder;
import com.ecomm.exception.EmailAlreadyExistsException;
import com.ecomm.exception.UserNotFoundException;
import com.ecomm.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserServiceController {

    @Autowired
    private UserDetailsBuilder builder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO user) throws EmailAlreadyExistsException {
        UserEntity userEntity = builder.createUserDTO(user);
        userService.registerUser(userEntity);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws UnAuthorizedException {
        //TODO-Include JWT oauth2.0
        userService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Login successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> fetchUserDetails(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.fetchUserDetails(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
