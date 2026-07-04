package io.savioromario10.api.controller.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandarError {

  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String path;
}