package com.ezyvet.domain.builder;

import java.time.LocalDate;
import java.util.UUID;

import com.ezyvet.domain.entity.Appointment;
import com.ezyvet.domain.entity.Pet;
import com.ezyvet.domain.entity.PetOwner;
import com.ezyvet.domain.entity.VaccinationCertificate;
import com.ezyvet.domain.entity.Vaccine;
import com.ezyvet.domain.entity.Veterinarian;

public final class VaccinationCertificateBuilder {

    private final VaccinationCertificate certificate;

    private VaccinationCertificateBuilder() {
        this.certificate = new VaccinationCertificate();
        this.certificate.setCertificateNumber(UUID.randomUUID().toString());
    }

    public static VaccinationCertificateBuilder builder() {
        return new VaccinationCertificateBuilder();
    }

    public VaccinationCertificateBuilder fromAppointment(Appointment appointment) {
        certificate.setAppointmentId(appointment.getId());
        certificate.setPetId(appointment.getPetId());
        certificate.setOwnerId(appointment.getOwnerId());
        certificate.setVeterinarianId(appointment.getVeterinarianId());
        certificate.setVaccineId(appointment.getVaccineId());
        return this;
    }

    public VaccinationCertificateBuilder withOwner(PetOwner owner) {
        certificate.setOwnerId(owner.getId());
        return this;
    }

    public VaccinationCertificateBuilder withPet(Pet pet) {
        certificate.setPetId(pet.getId());
        return this;
    }

    public VaccinationCertificateBuilder withVeterinarian(Veterinarian veterinarian) {
        certificate.setVeterinarianId(veterinarian.getId());
        return this;
    }

    public VaccinationCertificateBuilder withVaccine(Vaccine vaccine) {
        certificate.setVaccineId(vaccine.getId());
        return this;
    }

    public VaccinationCertificateBuilder certificateNumber(String number) {
        certificate.setCertificateNumber(number);
        return this;
    }

    public VaccinationCertificateBuilder validity(LocalDate issueDate, LocalDate expirationDate) {
        certificate.setIssueDate(issueDate);
        certificate.setExpirationDate(expirationDate);
        return this;
    }

    public VaccinationCertificate build() {
        return certificate;
    }
}
