package com.musala.drone.mapper;

import com.musala.drone.core.enums.DroneDeliveryStatus;
import com.musala.drone.core.enums.DroneModel;
import com.musala.drone.core.enums.DroneState;
import com.musala.drone.dto.drone.RegisterDroneInput;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDelivery;
import com.musala.drone.model.DroneDeliveryItem;
import com.musala.drone.model.Medication;

public class DroneMapper {
    public static Drone mapToDrone(RegisterDroneInput registerDroneInput) {
        DroneState state = (registerDroneInput.getBatteryLevel() >= 0.25) ? DroneState.LOADING : DroneState.IDLE;
        return Drone.builder()
            .serialNumber(registerDroneInput.getSerialNumber())
            .weightLimit(registerDroneInput.getWeightLimit())
            .batteryLevel(registerDroneInput.getBatteryLevel())
            .model(DroneModel.valueOf(registerDroneInput.getModel()))
            .state(state)
            .build();
    }

    public static DroneDelivery mapToDroneDelivery(Drone drone) {
        return DroneDelivery.builder()
            .drone(drone)
            .status(DroneDeliveryStatus.PENDING)
            .build();
    }

    public static DroneDeliveryItem mapToDroneDeliveryItem(Medication medication, Drone drone, DroneDelivery droneDelivery) {
        return DroneDeliveryItem.builder()
            .medication(medication)
            .drone(drone)
            .droneDelivery(droneDelivery)
            .build();
    }
}
