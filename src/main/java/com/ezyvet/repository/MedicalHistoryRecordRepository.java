package com.ezyvet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ezyvet.domain.entity.MedicalHistoryRecord;

public interface MedicalHistoryRecordRepository extends MongoRepository<MedicalHistoryRecord, String> {
    List<MedicalHistoryRecord> findByPetId(String petId);
}
