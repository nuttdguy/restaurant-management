package com.restaurant.repository;

import com.restaurant.domain.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public interface IPhotoRepo extends JpaRepository<Photo, Long> {
    Optional<Set<Photo>> findByRestaurantUuid(UUID restaurantId);
    Optional<Photo> findByName(String imageName);

}
