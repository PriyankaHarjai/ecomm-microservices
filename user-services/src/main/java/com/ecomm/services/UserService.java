package com.ecomm.services;

import com.ecomm.model.UserEntity;
import com.ecomm.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private boolean emailExists(String email) {
        return userDetailsRepository.findByEmail(email) != null;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
