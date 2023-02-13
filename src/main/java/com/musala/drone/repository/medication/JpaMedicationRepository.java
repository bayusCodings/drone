package com.musala.drone.repository.medication;

import com.musala.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaMedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByCode(String code);
    List<Medication> findByIdIn(List<Long> ids);
}
