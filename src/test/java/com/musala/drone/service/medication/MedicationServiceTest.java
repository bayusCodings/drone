package com.musala.drone.service.medication;

import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.mapper.MedicationMapper;
import com.musala.drone.model.Medication;
import com.musala.drone.repository.medication.MedicationRepository;
import com.musala.drone.util.MedicationUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

public class MedicationServiceTest {
    private MedicationService service;
    private MedicationRepository medicationRepository;

    @BeforeEach
    public void setUp() {
        medicationRepository = mock(MedicationRepository.class);
        service = new MedicationServiceImpl(medicationRepository);
    }

    @Test
    void validateMedicationCreatedSuccessfully() throws ConflictException {
        MedicationInput medicationInput = MedicationUtil.getMockedMedicationInput();
        Medication medication = MedicationMapper.mapToMedication(medicationInput);

        doReturn(Optional.empty()).when(medicationRepository).findByCode(medicationInput.getCode());
        doReturn(medication).when(medicationRepository).save(any());

        Medication result = service.createMedication(medicationInput);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(medicationInput.getName());
    }

    @Test
    void validateCreateMedicationThrowsExceptionForDuplicateCode() {
        MedicationInput medicationInput = MedicationUtil.getMockedMedicationInput();
        Medication medication = MedicationMapper.mapToMedication(medicationInput);

        doReturn(Optional.of(medication)).when(medicationRepository).findByCode(medicationInput.getCode());
        ConflictException exception = assertThrows(ConflictException.class, () -> service.createMedication(medicationInput));

        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateSuccessfulMedicationsFetch() {
        List<Medication> medicationList = MedicationUtil.getMockedMedicationList();

        doReturn(medicationList).when(medicationRepository).findAll();

        List<Medication> result = service.fetchMedications();
        Assertions.assertThat(result).size().isGreaterThan(0);
    }
}
