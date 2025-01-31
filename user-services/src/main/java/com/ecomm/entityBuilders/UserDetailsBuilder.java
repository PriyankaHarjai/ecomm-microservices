package com.ecomm.entityBuilders;

import com.ecomm.model.UserEntity;
import com.ecomm.model.UserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsBuilder {
    public UserEntity createUserDTO(UserRequestDTO user) {
        UserEntity userEntity = new UserEntity();
        if (user != null) {
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(user.getPassword());
        }
        return userEntity;
    }
}
