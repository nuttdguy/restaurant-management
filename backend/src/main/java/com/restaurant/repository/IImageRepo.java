package com.restaurant.repository;

import com.restaurant.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public interface IImageRepo extends JpaRepository<Image, Long> {
    Optional<Set<Image>> findByRestaurantUuid(UUID restaurantId);
    Optional<Image> findByName(String imageName);

}
