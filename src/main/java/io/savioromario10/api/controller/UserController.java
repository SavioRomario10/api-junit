package io.savioromario10.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.domain.dto.UserDto;
import io.savioromario10.api.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private UserService userService;

  private final String ID = "/{id}";

  @GetMapping(ID)
  public ResponseEntity<UserDto> findById(@PathVariable("id") Integer id) {
    return ResponseEntity.ok().body(
        mapper.map(userService.findById(id), UserDto.class)
    );
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> findAll(){
    List<User> users = userService.findAll();
    List<UserDto> userDto = 
      users.stream().map(
        user -> mapper.map(user, UserDto.class)
      ).collect(Collectors.toList());

    return ResponseEntity.ok().body(userDto);
  }

  @PostMapping
  public ResponseEntity<UserDto> create(@RequestBody UserDto user){
    User newUser = userService.create(user);
    URI uri = 
      ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(newUser.getId()).toUri();
    
    return ResponseEntity.created(uri).body(mapper.map(newUser, UserDto.class));
  }

  @PostMapping(ID)
  public ResponseEntity<UserDto> update(
    @PathVariable("id") Integer id, @RequestBody UserDto user)
  {
    user.setId(id);
    User updatedUser = userService.update(user);
    return ResponseEntity.ok().body(mapper.map(updatedUser, UserDto.class));
  }

  @DeleteMapping(ID)
  public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}  