package com.musala.drone.controller;

import com.musala.drone.core.base.Response;
import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.model.Medication;
import com.musala.drone.service.medication.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/medication")
public class MedicationController {
    private final MedicationService medicationService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<Medication>> createMedication(
        @Valid @RequestBody MedicationInput medicationInput) throws ConflictException {
        Medication medication = medicationService.createMedication(medicationInput);
        return new ResponseEntity<>(new Response<>(medication), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Response<List<Medication>>> fetchMedications() {
        List<Medication> medications = medicationService.fetchMedications();
        return new ResponseEntity<>(new Response<>(medications), HttpStatus.OK);
    }
}
