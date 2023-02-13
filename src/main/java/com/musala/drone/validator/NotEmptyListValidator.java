package com.musala.drone.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<Long>> {

    @Override
    public void initialize(NotEmptyList notEmptyList) {
    }

    @Override
    public boolean isValid(List<Long> objects, ConstraintValidatorContext context) {
        return !objects.isEmpty();
    }

}
