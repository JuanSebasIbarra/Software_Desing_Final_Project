package com.ezyvet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ezyvet.domain.entity.UserAccount;
import com.ezyvet.domain.enums.Role;
import com.ezyvet.repository.UserAccountRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedAdmin(UserAccountRepository repository, PasswordEncoder encoder) {
        return args -> {
            // Actualizar email antiguo si existe
            repository.findFirstByEmailIgnoreCase("admin@vetcarepro.local")
                .ifPresent(oldUser -> {
                    oldUser.setEmail("admin@ezyvet.local");
                    repository.save(oldUser);
                });
            
            // Crear admin con nuevo email si no existe
            repository.findFirstByEmailIgnoreCase("admin@ezyvet.local")
                .ifPresentOrElse(
                    user -> {},
                    () -> repository.save(UserAccount.builder()
                        .email("admin@ezyvet.local")
                        .fullName("Admin")
                        .password(encoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build())
                );
        };
    }
}
