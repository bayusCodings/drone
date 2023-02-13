package com.musala.drone.controller;

import com.musala.drone.core.base.Response;
import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.core.exception.NotFoundException;
import com.musala.drone.core.exception.UnprocessableEntityException;
import com.musala.drone.dto.drone.DroneBattery;
import com.musala.drone.dto.drone.DroneLoadedItem;
import com.musala.drone.dto.drone.LoadDroneInput;
import com.musala.drone.dto.drone.RegisterDroneInput;
import com.musala.drone.model.Drone;
import com.musala.drone.service.drone.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drone")
public class DroneController {
    private final DroneService droneService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<Drone>> registerDrone(@Valid @RequestBody RegisterDroneInput registerDroneInput)
        throws ConflictException {
        Drone drone = droneService.register(registerDroneInput);
        return new ResponseEntity<>(new Response<>(drone), HttpStatus.CREATED);
    }

    @PostMapping(path = "/load", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<String>> loadDrone(@Valid @RequestBody LoadDroneInput loadDroneInput)
        throws NotFoundException, UnprocessableEntityException {
        droneService.loadDrone(loadDroneInput);
        return new ResponseEntity<>(new Response<>(true, "Drone loaded successfully"),
            HttpStatus.CREATED);
    }

    @GetMapping(path = "/available", produces = "application/json")
    public ResponseEntity<Response<List<Drone>>> getLoadingAvailableDrones() {
        return new ResponseEntity<>(new Response<>(droneService.fetchLoadingAvailableDrones()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/loaded-items", produces = "application/json")
    public ResponseEntity<Response<DroneLoadedItem>> getDroneLoadedItems(@PathVariable Long id)
        throws NotFoundException {
        return new ResponseEntity<>(new Response<>(droneService.fetchDroneLoadedItems(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/battery", produces = "application/json")
    public ResponseEntity<Response<DroneBattery>> getDroneBatteryLevel(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(new Response<>(droneService.fetchDroneBatteryLevel(id)), HttpStatus.OK);
    }
}
