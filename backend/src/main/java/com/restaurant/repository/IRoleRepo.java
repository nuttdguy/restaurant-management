package com.restaurant.repository;

import com.restaurant.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IRoleRepo extends JpaRepository<Role, Long> {
    Role getReferenceByName(Class<Role> roleClass, String publicUser);
    Role findByName(String name);

    boolean existsByName(String name);
}