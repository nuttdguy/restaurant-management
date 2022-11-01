package com.restaurant.repository;

import com.restaurant.domain.model.SafetyLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ISafetyLicenseRepo extends JpaRepository<SafetyLicense, Long> {
}
