package com.musala.drone.service.drone;

import com.musala.drone.core.enums.DroneDeliveryStatus;
import com.musala.drone.core.enums.DroneState;
import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.core.exception.NotFoundException;
import com.musala.drone.core.exception.UnprocessableEntityException;
import com.musala.drone.dto.drone.DroneBattery;
import com.musala.drone.dto.drone.DroneLoadedItem;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final DroneDeliveryRepository droneDeliveryRepository;
    private final DroneDeliveryItemRepository droneDeliveryItemRepository;
    private final MedicationRepository medicationRepository;

    @Override
    public Drone register(RegisterDroneInput registerDroneInput) throws ConflictException {
        log.info("Registering drone with input: {}", registerDroneInput);

        Optional<Drone> optionalDrone = droneRepository.findBySerialNumber(registerDroneInput.getSerialNumber());
        if (optionalDrone.isPresent()) {
            log.info("Duplicate serial number: {}", registerDroneInput.getSerialNumber());
            throw new ConflictException("Duplicate serial number: "+ registerDroneInput.getSerialNumber());
        }

        return droneRepository.save(DroneMapper.mapToDrone(registerDroneInput));
    }

    @Override
    public List<Drone> fetchLoadingAvailableDrones() {
        log.info("fetching list of drones available for loading");
        return droneRepository.findByState(DroneState.LOADING);
    }

    @Override
    public DroneBattery fetchDroneBatteryLevel(Long droneId) throws NotFoundException {
        log.info("fetching battery level for drone with id: {}", droneId);
        Drone drone = getDrone(droneId);
        return DroneBattery.builder().batteryLevel(drone.getBatteryLevel()).build();
    }

    @Override
    public DroneLoadedItem fetchDroneLoadedItems(Long droneId) throws NotFoundException {
        log.info("fetching drone loaded item for droneId: {}", droneId);
        Drone drone = getDrone(droneId);

        // fetch ongoing drone delivery (status != COMPLETE)
        Optional<DroneDelivery> optionalDroneDelivery = droneDeliveryRepository
            .findByDroneAndStatusIsNot(drone, DroneDeliveryStatus.COMPLETE);
        if (optionalDroneDelivery.isEmpty()) {
            throw new NotFoundException("drone does not have an ongoing delivery");
        }

        List<DroneDeliveryItem> items = droneDeliveryItemRepository.findByDroneDelivery(optionalDroneDelivery.get());
        return DroneLoadedItem.builder().drone(drone).items(items).build();
    }

    @Override
    public void loadDrone(LoadDroneInput loadDroneInput) throws NotFoundException, UnprocessableEntityException {
        log.info("loading drone with input: {}", loadDroneInput);

        Drone drone = getDrone(loadDroneInput.getDroneId());
        if (!drone.getState().equals(DroneState.LOADING)) {
            throw new UnprocessableEntityException("drone is not in LOADING state");
        }

        List<Long> medicationIds = loadDroneInput.getMedicationIds();
        processDroneLoad(drone, medicationIds);
    }

    private Drone getDrone(Long droneId) throws NotFoundException {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);
        if (optionalDrone.isEmpty()) {
            log.info("drone not found with id: {}", droneId);
            throw new NotFoundException("drone not found with id: "+ droneId);
        }

        return optionalDrone.get();
    }

    private void processDroneLoad(Drone drone, List<Long> medicationIds) throws NotFoundException, UnprocessableEntityException {
        List<Medication> medications = medicationRepository.findByIdIn(medicationIds);
        List<Long> foundIds = medications.stream().map(Medication::getId).toList();

        // Remove all foundIds so we are left with notFoundIds
        List<Long> notFoundIds = new ArrayList<>(medicationIds);
        notFoundIds.removeAll(foundIds);

        if (notFoundIds.size() > 0) {
            throw new NotFoundException("could not find medication for ids: "+ notFoundIds);
        }

        float totalMedicationWeight = medications.stream().map(Medication::getWeight).reduce(0f, Float::sum);
        if (totalMedicationWeight > drone.getWeightLimit()) {
            throw new UnprocessableEntityException("loaded medication weight exceeds the drone weight limit of "
                + drone.getWeightLimit() +"gr");
        }

        saveDroneLoadEntries(drone, medications);
    }

    private void saveDroneLoadEntries(Drone drone, List<Medication> medications) {
        // update drone state to LOADED
        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);

        // create drone delivery entry
        DroneDelivery droneDelivery = droneDeliveryRepository.save(DroneMapper.mapToDroneDelivery(drone));

        // save drone delivery items
        medications.parallelStream().forEach(it ->
            droneDeliveryItemRepository.save(DroneMapper.mapToDroneDeliveryItem(it, drone, droneDelivery))
        );
    }
}
