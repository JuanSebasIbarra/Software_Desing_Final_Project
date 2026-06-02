package com.ezyvet.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ezyvet.domain.entity.Veterinarian;

public interface VeterinarianRepository extends MongoRepository<Veterinarian, String> {
    Optional<Veterinarian> findByLicenseNumber(String licenseNumber);

    Optional<Veterinarian> findByUserAccountId(String userAccountId);
}
