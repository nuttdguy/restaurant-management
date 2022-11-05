package com.restaurant.repository;

import com.restaurant.domain.model.UuidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ITokenRepo extends JpaRepository<UuidToken, Long> {
    Optional<UuidToken> findByToken(UUID token);
}
