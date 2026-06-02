package com.ezyvet.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ezyvet.domain.enums.AppointmentStatus;
import com.ezyvet.domain.enums.AppointmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    private String ownerId;

    private String petId;

    private String veterinarianId;

    private AppointmentType type;

    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    private LocalDateTime appointmentDate;

    private String reason;

    private String vaccineId;

    private String notes;

    @Builder.Default
    private boolean reminderSent = false;
}
