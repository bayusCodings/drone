package com.musala.drone.mapper;

import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.model.Medication;

public class MedicationMapper {
    public static Medication mapToMedication(MedicationInput medicationInput) {
        return Medication.builder()
            .name(medicationInput.getName())
            .weight(medicationInput.getWeight())
            .code(medicationInput.getCode())
            .imageUrl(medicationInput.getImageUrl())
            .build();
    }
}
