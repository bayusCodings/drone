package com.musala.drone.dto.medication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationInput {
    @NotBlank(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Invalid name, only letters, numbers, hyphen and underscore are allowed")
    private String name;

    @Min(1)
    @NotNull(message = "weight is required")
    private float weight;

    @NotBlank(message = "code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Invalid code, only upper case letters, underscore and numbers are allowed")
    private String code;

    @NotBlank(message = "imageUrl is required")
    private String imageUrl;
}
