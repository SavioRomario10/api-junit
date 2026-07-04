package io.savioromario10.api.controller.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.savioromario10.api.service.exception.DataIntegratyViolationException;
import io.savioromario10.api.service.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
    StandarError error = new StandarError(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND.value(),
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DataIntegratyViolationException.class)
  public ResponseEntity<StandarError> dataIntegratyViolation(DataIntegratyViolationException e, HttpServletRequest request) {
    StandarError error = new StandarError(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}