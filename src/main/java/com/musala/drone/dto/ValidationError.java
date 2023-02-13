package com.musala.drone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationError {
    private String field;
    private String error;
}

