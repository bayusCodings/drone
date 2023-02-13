package com.musala.drone.service.drone;

import com.musala.drone.core.enums.DroneState;
import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.core.exception.NotFoundException;
import com.musala.drone.core.exception.UnprocessableEntityException;
import com.musala.drone.dto.drone.DroneBattery;
import com.musala.drone.dto.drone.LoadDroneInput;
import com.musala.drone.dto.drone.RegisterDroneInput;
import com.musala.drone.mapper.DroneMapper;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDelivery;
import com.musala.drone.model.DroneDeliveryItem;
import com.musala.drone.model.Medication;
import com.musala.drone.repository.drone.DroneDeliveryItemRepository;
import com.musala.drone.repository.drone.DroneDeliveryRepository;
import com.musala.drone.repository.drone.DroneRepository;
import com.musala.drone.repository.medication.MedicationRepository;
import com.musala.drone.util.DroneUtil;
import com.musala.drone.util.MedicationUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class DroneServiceTest {
    private DroneService service;
    private DroneRepository droneRepository;
    private DroneDeliveryRepository droneDeliveryRepository;
    private DroneDeliveryItemRepository droneDeliveryItemRepository;
    private MedicationRepository medicationRepository;

    @BeforeEach
    public void setUp() {
        droneRepository = mock(DroneRepository.class);
        droneDeliveryRepository = mock(DroneDeliveryRepository.class);
        droneDeliveryItemRepository = mock(DroneDeliveryItemRepository.class);
        medicationRepository = mock(MedicationRepository.class);

        service = new DroneServiceImpl(
            droneRepository,
            droneDeliveryRepository,
            droneDeliveryItemRepository,
            medicationRepository
        );
    }

    @Test
    void validateSuccessfulDroneRegistration() throws ConflictException {
        RegisterDroneInput registerDroneInput = DroneUtil.getMockedRegisterDroneInput();
        Drone drone = DroneMapper.mapToDrone(registerDroneInput);

        doReturn(Optional.empty()).when(droneRepository).findBySerialNumber(registerDroneInput.getSerialNumber());
        doReturn(drone).when(droneRepository).save(any());

        Drone result = service.register(registerDroneInput);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getSerialNumber()).isEqualTo(registerDroneInput.getSerialNumber());
    }

    @Test
    void validateDroneRegistrationThrowsExceptionForDuplicateSerialNumber() {
        RegisterDroneInput registerDroneInput = DroneUtil.getMockedRegisterDroneInput();
        Drone drone = DroneMapper.mapToDrone(registerDroneInput);

        doReturn(Optional.of(drone)).when(droneRepository).findBySerialNumber(registerDroneInput.getSerialNumber());
        ConflictException exception = assertThrows(ConflictException.class, () -> service.register(registerDroneInput));

        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateDroneBatteryLevelReturnedForValidDroneId() throws NotFoundException {
        Drone drone = DroneUtil.getMockedDrone();
        doReturn(Optional.of(drone)).when(droneRepository).findById(anyLong());

        DroneBattery result = service.fetchDroneBatteryLevel(anyLong());
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void validateDroneBatteryLevelThrowsExceptionForInvalidDroneId() {
        doReturn(Optional.empty()).when(droneRepository).findById(anyLong());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.fetchDroneBatteryLevel(anyLong()));
        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateSuccessfulDroneLoad() throws NotFoundException, UnprocessableEntityException {
        LoadDroneInput loadDroneInput = DroneUtil.getMockedLoadDroneInput();
        Drone drone = DroneUtil.getMockedDrone();
        drone.setState(DroneState.LOADING);
        List<Medication> medications = MedicationUtil.getMockedMedicationList();
        medications.get(0).setId(loadDroneInput.getMedicationIds().get(0));

        doReturn(Optional.of(drone)).when(droneRepository).findById(anyLong());
        doReturn(medications).when(medicationRepository).findByIdIn(loadDroneInput.getMedicationIds());

        doReturn(drone).when(droneRepository).save(any());
        doReturn(new DroneDelivery()).when(droneDeliveryRepository).save(any());
        doReturn(new DroneDeliveryItem()).when(droneDeliveryItemRepository).save(any());

        service.loadDrone(loadDroneInput);
    }

    @Test
    void validateDroneLoadThrowsExceptionForInvalidDroneId() {
        LoadDroneInput loadDroneInput = DroneUtil.getMockedLoadDroneInput();
        doReturn(Optional.empty()).when(droneRepository).findById(loadDroneInput.getDroneId());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.loadDrone(loadDroneInput));
        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateDroneLoadThrowsExceptionWhenDroneNotInLoadingState() {
        LoadDroneInput loadDroneInput = DroneUtil.getMockedLoadDroneInput();
        Drone drone = DroneUtil.getMockedDrone();
        drone.setState(DroneState.RETURNING);

        doReturn(Optional.of(drone)).when(droneRepository).findById(loadDroneInput.getDroneId());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
            () -> service.loadDrone(loadDroneInput));
        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateDroneLoadThrowsExceptionWhenProvidedInvalidMedicationId() {
        LoadDroneInput loadDroneInput = DroneUtil.getMockedLoadDroneInput();
        Drone drone = DroneUtil.getMockedDrone();
        drone.setState(DroneState.LOADING);

        doReturn(Optional.of(drone)).when(droneRepository).findById(loadDroneInput.getDroneId());
        doReturn(new ArrayList<Medication>()).when(medicationRepository).findByIdIn(loadDroneInput.getMedicationIds());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.loadDrone(loadDroneInput));
        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
    }

    @Test
    void validateDroneLoadThrowsExceptionWhenProvidedMedicationsWeightExceedDroneWeightLimit() {
        LoadDroneInput loadDroneInput = DroneUtil.getMockedLoadDroneInput();
        Drone drone = DroneUtil.getMockedDrone();
        drone.setState(DroneState.LOADING);
        List<Medication> medications = MedicationUtil.getMockedMedicationList();
        medications.get(0).setId(loadDroneInput.getMedicationIds().get(0));
        medications.get(0).setWeight(600);

        doReturn(Optional.of(drone)).when(droneRepository).findById(loadDroneInput.getDroneId());
        doReturn(medications).when(medicationRepository).findByIdIn(loadDroneInput.getMedicationIds());

        UnprocessableEntityException exception = assertThrows(UnprocessableEntityException.class,
            () -> service.loadDrone(loadDroneInput));
        Assertions.assertThat(exception.getLocalizedMessage()).isNotNull();
        Assertions.assertThat(exception.getLocalizedMessage()).isEqualTo("loaded medication weight exceeds the " +
            "drone weight limit of "+ drone.getWeightLimit() +"gr");
    }
}
