package com.ezyvet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ezyvet.domain.entity.Vaccine;

public interface VaccineRepository extends MongoRepository<Vaccine, String> {
    boolean existsByNameIgnoreCase(String name);
}
