package com.musala.drone.repository.drone;

import com.musala.drone.core.enums.DroneState;
import com.musala.drone.model.Drone;

import java.util.List;
import java.util.Optional;

public interface DroneRepository {
    Drone save(Drone drone);
    Optional<Drone> findById(Long id);
    Optional<Drone> findBySerialNumber(String serialNumber);
    List<Drone> findByState(DroneState state);
    List<Drone> findAll();
}
