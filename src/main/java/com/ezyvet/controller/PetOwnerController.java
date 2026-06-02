package com.ezyvet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezyvet.domain.entity.PetOwner;
import com.ezyvet.service.PetOwnerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    @GetMapping
    public List<PetOwner> list() {
        return petOwnerService.findAll();
    }
}
