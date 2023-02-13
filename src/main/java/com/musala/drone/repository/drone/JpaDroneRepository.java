package com.musala.drone.repository.drone;

import com.musala.drone.core.enums.DroneState;
import com.musala.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaDroneRepository extends JpaRepository<Drone, Long> {
    Optional<Drone> findBySerialNumber(String serialNumber);
    List<Drone> findByState(DroneState state);
}
