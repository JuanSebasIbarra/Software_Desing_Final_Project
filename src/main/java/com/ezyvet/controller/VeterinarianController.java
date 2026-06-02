package com.ezyvet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezyvet.domain.entity.Veterinarian;
import com.ezyvet.service.VeterinarianService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/veterinarians")
@RequiredArgsConstructor
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

    @GetMapping
    public List<Veterinarian> list() {
        return veterinarianService.findAll();
    }
}
