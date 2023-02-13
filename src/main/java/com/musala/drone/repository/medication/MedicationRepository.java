package com.musala.drone.repository.medication;

import com.musala.drone.model.Medication;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
    Medication save(Medication medication);
    List<Medication> findByIdIn(List<Long> ids);
    Optional<Medication> findByCode(String code);
    List<Medication> findAll();
}
