package com.ecomm.services;

import com.ecomm.exception.UserNotFoundException;
import com.ecomm.model.LoginRequest;
import com.ecomm.model.User;
import com.ecomm.model.UserEntity;
import com.ecomm.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(UserEntity userEntity) throws Exception {
        if (emailExists(userEntity.getEmail())) {
            throw new Exception("Email already exists");
        }
        userEntity.setPassword(encryptPassword(userEntity.getPassword()));
        userDetailsRepository.save(userEntity);
    }

    public void authenticate(LoginRequest loginRequest)  throws Exception{
        //whether email/username exists, if it's not throw exception
        UserEntity user = userDetailsRepository.findByEmail(loginRequest.getUsername());
        if(user!=null){
            String hashedPassword = user.getPassword();
            if(!passwordEncoder.matches(loginRequest.getPassword(), hashedPassword)){
                throw new Exception("Invalid credentials");
            }
        }else{
            throw new Exception("Email doesn't exists");
        }
    }

    private boolean emailExists(String email) {
        return userDetailsRepository.findByEmail(email) != null;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User fetchUserDetails(Long userId) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userDetailsRepository.findById(userId);
        return userEntity.map(this::buildUserDetails)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }


    private User buildUserDetails(UserEntity userEntity1) {
        User user = new User();
        user.setEmail(userEntity1.getEmail());
        user.setFirstName(userEntity1.getFirstName());
        user.setLastName(userEntity1.getLastName());
        return user;
    }
}
