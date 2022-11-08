package com.restaurant.repository;

import com.restaurant.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public interface IRestaurantRepo extends JpaRepository<Restaurant, UUID> {

    void deleteByName(String name);
    boolean existsByName(String name);
    Set<Restaurant> findAllByName(String name);
    Set<Restaurant> findByUserUsername(String username);
    Optional<Restaurant> findByPhone(String phone);
    Set<Restaurant> findFirst5ByUserUsername(String username);
    Set<Restaurant> findFirst5ByUserUuid(UUID uuid);
}
