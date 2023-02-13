package com.musala.drone.cron;

import com.musala.drone.core.enums.DroneDeliveryStatus;
import com.musala.drone.core.enums.DroneState;
import com.musala.drone.model.Drone;
import com.musala.drone.model.DroneDelivery;
import com.musala.drone.repository.drone.DroneDeliveryRepository;
import com.musala.drone.repository.drone.DroneRepository;
import com.musala.drone.service.audit.AuditTrailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DroneTask {
    private final DroneRepository droneRepository;
    private final DroneDeliveryRepository droneDeliveryRepository;
    private final AuditTrailService auditTrailService;

    // runs every minute
    @Scheduled(cron = "0 * * ? * *")
    public void checkBatteryLevel() {
        List<Drone> droneList = droneRepository.findAll();
        droneList.parallelStream().forEach(it -> {
            if (it.getBatteryLevel() < 0.25) {
                auditTrailService.createAuditTrail(
                    "Battery level of drone " + it.getSerialNumber() + " is low: " + it.getBatteryLevel() + "%");
            }
        });
    }

    // runs every 2 minutes
    @Scheduled(cron = "0 0/2 * ? * *")
    public void simulateDroneTrips() {
        log.info("simulating drone delivery trips");
        // List of non-completed drone trips
        List<DroneDelivery> droneDeliveries = droneDeliveryRepository.findByStatusIsNot(DroneDeliveryStatus.COMPLETE);
        droneDeliveries.parallelStream().forEach(it -> {
            Drone drone = it.getDrone();

            if (drone.getState().equals(DroneState.LOADED)) {
                drone.setState(DroneState.DELIVERING);
                it.setStatus(DroneDeliveryStatus.IN_PROGRESS);
                log.info("updating drone {} to {} state", drone.getSerialNumber(), drone.getState());
            }
            if (drone.getState().equals(DroneState.DELIVERING)) {
                drone.setState(DroneState.DELIVERED);
                it.setStatus(DroneDeliveryStatus.IN_PROGRESS);
                log.info("updating drone {} to {} state", drone.getSerialNumber(), drone.getState());
            }
            if (drone.getState().equals(DroneState.DELIVERED)) {
                drone.setState(DroneState.RETURNING);
                it.setStatus(DroneDeliveryStatus.IN_PROGRESS);
                log.info("updating drone {} to {} state", drone.getSerialNumber(), drone.getState());
            }
            if (drone.getState().equals(DroneState.RETURNING)) {
                DroneState state = (drone.getBatteryLevel() >= 0.25) ? DroneState.LOADING : DroneState.IDLE;
                drone.setState(state);
                it.setStatus(DroneDeliveryStatus.COMPLETE);
                log.info("updating drone {} to {} state", drone.getSerialNumber(), state);
            }

            droneRepository.save(drone);
            droneDeliveryRepository.save(it);
        });
    }
}
