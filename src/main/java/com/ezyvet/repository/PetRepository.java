package com.ezyvet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ezyvet.domain.entity.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByOwnerId(String ownerId);
}
