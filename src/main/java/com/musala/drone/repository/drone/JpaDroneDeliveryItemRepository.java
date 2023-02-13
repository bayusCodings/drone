package com.musala.drone.repository.drone;

import com.musala.drone.model.DroneDelivery;
import com.musala.drone.model.DroneDeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDroneDeliveryItemRepository extends JpaRepository<DroneDeliveryItem, Long> {
    List<DroneDeliveryItem> findByDroneDelivery(DroneDelivery droneDelivery);
}
