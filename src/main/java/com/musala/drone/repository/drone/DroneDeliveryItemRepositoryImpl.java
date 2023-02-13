package com.musala.drone.repository.drone;

import com.musala.drone.model.DroneDelivery;
import com.musala.drone.model.DroneDeliveryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DroneDeliveryItemRepositoryImpl implements DroneDeliveryItemRepository {
    private final JpaDroneDeliveryItemRepository repository;

    @Override
    public DroneDeliveryItem save(DroneDeliveryItem droneDeliveryItem) {
        return repository.save(droneDeliveryItem);
    }

    @Override
    public List<DroneDeliveryItem> findByDroneDelivery(DroneDelivery droneDelivery) {
        return repository.findByDroneDelivery(droneDelivery);
    }
}
