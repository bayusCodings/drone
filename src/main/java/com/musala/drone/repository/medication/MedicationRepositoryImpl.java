package com.musala.drone.repository.medication;

import com.musala.drone.model.Medication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicationRepositoryImpl implements  MedicationRepository {
    private final JpaMedicationRepository repository;

    @Override
    public Medication save(Medication medication) {
        return repository.save(medication);
    }

    @Override
    public List<Medication> findByIdIn(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    @Override
    public Optional<Medication> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<Medication> findAll() {
        return repository.findAll();
    }
}
