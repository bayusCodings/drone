package com.musala.drone.util;

import com.musala.drone.core.enums.DroneModel;
import com.musala.drone.dto.drone.LoadDroneInput;
import com.musala.drone.dto.drone.RegisterDroneInput;
import com.musala.drone.model.Drone;

import java.util.ArrayList;
import java.util.List;

public class DroneUtil {
    public static RegisterDroneInput getMockedRegisterDroneInput() {
        return RegisterDroneInput.builder()
            .serialNumber("2938-9209")
            .weightLimit(350)
            .batteryLevel(0.7f)
            .model(DroneModel.Cruiserweight.toString())
            .build();
    }

    public static Drone getMockedDrone() {
        return Drone.builder()
            .serialNumber("2938-9209")
            .weightLimit(350)
            .batteryLevel(0.7f)
            .model(DroneModel.Cruiserweight)
            .build();
    }

    public static List<Drone> getMockedDroneList() {
        List<Drone> droneList = new ArrayList<>();
        droneList.add(DroneUtil.getMockedDrone());
        return droneList;
    }

    public static LoadDroneInput getMockedLoadDroneInput() {
        List<Long> medicationIds = new ArrayList<>();
        medicationIds.add(20L);

        return LoadDroneInput.builder()
            .droneId(2L)
            .medicationIds(medicationIds)
            .build();
    }
}
