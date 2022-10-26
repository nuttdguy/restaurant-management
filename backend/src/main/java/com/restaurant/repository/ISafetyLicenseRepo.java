package com.restaurant.repository;

import com.restaurant.domain.model.SafetyLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISafetyLicenseRepo extends JpaRepository<SafetyLicense, Long> {
}
