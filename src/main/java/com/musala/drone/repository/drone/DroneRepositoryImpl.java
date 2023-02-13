package com.musala.drone.repository.drone;

import com.musala.drone.core.enums.DroneState;
import com.musala.drone.model.Drone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DroneRepositoryImpl implements DroneRepository {
    private final JpaDroneRepository repository;

    @Override
    public Drone save(Drone drone) {
        return repository.save(drone);
    }

    @Override
    public Optional<Drone> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Drone> findBySerialNumber(String serialNumber) {
        return repository.findBySerialNumber(serialNumber);
    }

    @Override
    public List<Drone> findByState(DroneState state) {
        return repository.findByState(state);
    }

    @Override
    public List<Drone> findAll() {
        return repository.findAll();
    }
}
