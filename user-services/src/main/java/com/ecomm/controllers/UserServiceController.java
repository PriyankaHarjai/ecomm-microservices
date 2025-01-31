package com.ecomm.controllers;

import com.ecomm.entityBuilders.UserDetailsBuilder;
import com.ecomm.exception.UserNotFoundException;
import com.ecomm.model.LoginRequest;
import com.ecomm.model.User;
import com.ecomm.model.UserEntity;
import com.ecomm.model.UserRequestDTO;
import com.ecomm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserServiceController {
    //TODO- better exception handling
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
       //TODO-Include JWT oauth2.0
        try{
            userService.authenticate(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Login successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: "+e.getMessage());
        }
    }

    @GetMapping("/{userId}")
   public ResponseEntity<?> fetchUserDetails(@PathVariable Long userId){
        try{
            User user = userService.fetchUserDetails(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
   }

}
