package com.musala.drone.dto.drone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneBattery {
    private float batteryLevel;
}
