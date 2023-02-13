package com.musala.drone.dto.drone;

import com.musala.drone.validator.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoadDroneInput {
    @NotNull(message = "droneId is required")
    private Long droneId;

    @NotEmptyList(message = "medicationIds list is empty")
    private List<Long> medicationIds;
}
