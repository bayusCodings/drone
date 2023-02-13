package com.musala.drone.core.advice;

import com.musala.drone.core.base.Response;
import com.musala.drone.core.exception.BadRequestException;
import com.musala.drone.core.exception.ConflictException;
import com.musala.drone.core.exception.NotFoundException;
import com.musala.drone.core.exception.UnprocessableEntityException;
import com.musala.drone.dto.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<?>> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(new Response<>(false, "Validation Error", validationErrors),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response<?>> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(new Response<>(false, ex.getLocalizedMessage()),
            HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new Response<>(false, ex.getLocalizedMessage()),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<?>> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new Response<>(false, ex.getLocalizedMessage()),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Response<?>> handlUnprocessableEntityException(UnprocessableEntityException ex) {
        return new ResponseEntity<>(new Response<>(false, ex.getLocalizedMessage()),
            HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
