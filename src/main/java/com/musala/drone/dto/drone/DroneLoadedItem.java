package com.musala.drone.dto.drone;

import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDeliveryItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DroneLoadedItem {
    private Drone drone;
    private List<DroneDeliveryItem> items;
}
