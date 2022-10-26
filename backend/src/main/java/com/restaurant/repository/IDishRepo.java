package com.restaurant.repository;

import com.restaurant.domain.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public interface IDishRepo extends JpaRepository<Dish, Long> {
    Set<Dish> findByRestaurantUuid(UUID restaurantId);
}
