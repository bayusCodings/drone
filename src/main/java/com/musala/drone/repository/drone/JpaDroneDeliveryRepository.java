package com.musala.drone.repository.drone;

import com.musala.drone.core.enums.DroneDeliveryStatus;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaDroneDeliveryRepository extends JpaRepository<DroneDelivery, Long> {
    Optional<DroneDelivery> findByDroneAndStatusIsNot(Drone drone, DroneDeliveryStatus status);
    List<DroneDelivery> findByStatusIsNot(DroneDeliveryStatus status);
}
