package com.musala.drone.service.drone;

import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.core.exception.NotFoundException;
import com.musala.drone.core.exception.UnprocessableEntityException;
import com.musala.drone.dto.drone.DroneBattery;
import com.musala.drone.dto.drone.DroneLoadedItem;
import com.musala.drone.dto.drone.LoadDroneInput;
import com.musala.drone.dto.drone.RegisterDroneInput;
import com.musala.drone.model.Drone;

import java.util.List;

public interface DroneService {
    Drone register(RegisterDroneInput registerDroneInput) throws ConflictException;
    List<Drone> fetchLoadingAvailableDrones();
    DroneBattery fetchDroneBatteryLevel(Long droneId) throws NotFoundException;
    DroneLoadedItem fetchDroneLoadedItems(Long droneId) throws NotFoundException;
    void loadDrone(LoadDroneInput loadDroneInput) throws NotFoundException, UnprocessableEntityException;
}
