package com.musala.drone.repository.drone;

import com.musala.drone.core.enums.DroneDeliveryStatus;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DroneDeliveryRepositoryImpl implements DroneDeliveryRepository {
    private final JpaDroneDeliveryRepository repository;

    @Override
    public DroneDelivery save(DroneDelivery droneDelivery) {
        return repository.save(droneDelivery);
    }

    @Override
    public Optional<DroneDelivery> findByDroneAndStatusIsNot(Drone drone, DroneDeliveryStatus status) {
        return repository.findByDroneAndStatusIsNot(drone, status);
    }

    @Override
    public List<DroneDelivery> findByStatusIsNot(DroneDeliveryStatus status) {
        return repository.findByStatusIsNot(status);
    }
}
