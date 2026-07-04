package io.savioromario10.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.savioromario10.api.domain.dto.UserDto;
import io.savioromario10.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(
        mapper.map(userService.findById(id), UserDto.class)
    );
  }
}