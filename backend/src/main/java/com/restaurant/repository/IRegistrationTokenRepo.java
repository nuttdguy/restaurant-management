package com.restaurant.repository;

import com.restaurant.domain.model.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRegistrationTokenRepo extends JpaRepository<RegistrationToken, UUID> {
}
