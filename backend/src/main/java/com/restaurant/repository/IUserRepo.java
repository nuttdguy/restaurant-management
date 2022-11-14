package com.restaurant.repository;

import com.restaurant.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface IUserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String theUsername);
    boolean existsByUsername(String username);
}
