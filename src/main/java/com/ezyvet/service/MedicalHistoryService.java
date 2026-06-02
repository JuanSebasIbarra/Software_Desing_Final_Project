package com.ezyvet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezyvet.domain.entity.MedicalHistoryRecord;
import com.ezyvet.exception.ResourceNotFoundException;
import com.ezyvet.repository.MedicalHistoryRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {

    private final MedicalHistoryRecordRepository medicalHistoryRecordRepository;

    public MedicalHistoryRecord create(MedicalHistoryRecord record) {
        return medicalHistoryRecordRepository.save(record);
    }

    public List<MedicalHistoryRecord> findByPet(String petId) {
        return medicalHistoryRecordRepository.findByPetId(petId);
    }

    public MedicalHistoryRecord findById(String id) {
        return medicalHistoryRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medical history not found: " + id));
    }

    public void delete(String id) {
        medicalHistoryRecordRepository.deleteById(id);
    }
}
