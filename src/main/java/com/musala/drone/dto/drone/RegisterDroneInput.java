package com.musala.drone.dto.drone;

import com.musala.drone.core.enums.DroneModel;
import com.musala.drone.validator.EnumValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDroneInput {
    @NotBlank(message = "serialNumber is required")
    @Size(max = 100, message = "Exceeded max length 100")
    private String serialNumber;

    @Min(1)
    @Max(500)
    @NotNull(message = "weightLimit is required")
    private float weightLimit;

    @Min(0)
    @Max(1)
    private float batteryLevel;

    @EnumValidator(type = DroneModel.class, message = "invalid model type")
    private String model;
}
