package com.restaurant.repository;

import com.restaurant.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String theUsername);

    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}
