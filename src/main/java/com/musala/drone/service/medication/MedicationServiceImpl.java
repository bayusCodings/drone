package com.musala.drone.service.medication;

import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.mapper.MedicationMapper;
import com.musala.drone.model.Medication;
import com.musala.drone.repository.medication.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    @Override
    public Medication createMedication(MedicationInput medicationInput) throws ConflictException {
        log.info("Creating medication with input: {}", medicationInput);

        Optional<Medication> optionalMedication = medicationRepository.findByCode(medicationInput.getCode());
        if (optionalMedication.isPresent()) {
            log.info("Duplicate medication code: {}", medicationInput.getCode());
            throw new ConflictException("Duplicate code: "+ medicationInput.getCode());
        }

        return medicationRepository.save(MedicationMapper.mapToMedication(medicationInput));
    }

    @Override
    public List<Medication> fetchMedications() {
        return medicationRepository.findAll();
    }
}
