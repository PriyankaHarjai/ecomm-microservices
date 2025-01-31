package com.ecomm.repository;

import com.ecomm.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
