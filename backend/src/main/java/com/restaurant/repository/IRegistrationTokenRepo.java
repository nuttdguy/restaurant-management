package com.restaurant.repository;

import com.restaurant.domain.model.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface IRegistrationTokenRepo extends JpaRepository<RegistrationToken, Long> {
    Optional<RegistrationToken> findByToken(UUID token);
}
