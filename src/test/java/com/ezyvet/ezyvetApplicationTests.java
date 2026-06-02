package com.ezyvet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ezyvet.repository.AppointmentRepository;
import com.ezyvet.repository.MedicalHistoryRecordRepository;
import com.ezyvet.repository.PetOwnerRepository;
import com.ezyvet.repository.PetRepository;
import com.ezyvet.repository.UserAccountRepository;
import com.ezyvet.repository.VaccinationCertificateRepository;
import com.ezyvet.repository.VaccineRepository;
import com.ezyvet.repository.VeterinarianRepository;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,"
        + "de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration"
})
@ActiveProfiles("test")
class ezyvetApplicationTests {

    @MockBean
    PetOwnerRepository petOwnerRepository;
    @MockBean
    PetRepository petRepository;
    @MockBean
    VeterinarianRepository veterinarianRepository;
    @MockBean
    AppointmentRepository appointmentRepository;
    @MockBean
    VaccineRepository vaccineRepository;
    @MockBean
    VaccinationCertificateRepository vaccinationCertificateRepository;
    @MockBean
    MedicalHistoryRecordRepository medicalHistoryRecordRepository;
    @MockBean
    UserAccountRepository userAccountRepository;

    @Test
    void contextLoads() {
    }
}
