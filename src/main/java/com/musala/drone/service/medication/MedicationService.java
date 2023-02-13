package com.musala.drone.service.medication;

import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.model.Medication;

import java.util.List;

public interface MedicationService {
    Medication createMedication(MedicationInput medicationInput) throws ConflictException;
    List<Medication> fetchMedications();
}
