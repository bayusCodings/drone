package com.musala.drone.repository.drone;

import com.musala.drone.model.DroneDelivery;
import com.musala.drone.model.DroneDeliveryItem;

import java.util.List;

public interface DroneDeliveryItemRepository {
    DroneDeliveryItem save(DroneDeliveryItem droneDeliveryItem);
    List<DroneDeliveryItem> findByDroneDelivery(DroneDelivery droneDelivery);
}
