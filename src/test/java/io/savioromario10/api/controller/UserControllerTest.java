package io.savioromario10.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.domain.dto.UserDto;
import io.savioromario10.api.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@SpringBootTest
public class UserControllerTest {

  private static final Integer ID = 1;
  private static final String NAME = "Savio";
  private static final String EMAIL = "mario@sv.com";
  private static final String PASSWORD = "12345678";

  private User user;
  private UserDto userDto;
  private Optional<User> optionalUser;

  @InjectMocks
  private UserController userController;

  @Mock
  private UserServiceImpl userService;

  @Mock
  private ModelMapper modelMapper;

  @BeforeEach
  public void setUp() {
    openMocks(this);
    startUser();
  }

  private void startUser(){
    user = new User(ID, NAME, EMAIL, PASSWORD);
    userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
  }

  @Test
  void whenFindByIdSucess(){
    when(userService.findById(anyInt())).thenReturn(user);
    when(modelMapper.map(any(), any())).thenReturn(userDto);

    ResponseEntity<UserDto> response = userController.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDto.class, response.getBody().getClass());
  }

  @Test
  void whenFindAllListUser(){
    when(userService.findAll()).thenReturn(List.of(user));
    when(modelMapper.map(any(), any())).thenReturn(userDto);

    ResponseEntity<List<UserDto>> response = userController.findAll();

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(ArrayList.class, response.getBody().getClass());
  }

  @Test
  void whenCreateThenReturnCreated() {
    when(userService.create(any())).thenReturn(user);

    ResponseEntity<UserDto> response = userController.create(userDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(UserDto.class, response.getBody().getClass());
  }

  @Test
  void whenUpdateThenReturnCreated() {
    when(userService.update(userDto)).thenReturn(user);
    when(modelMapper.map(any(), any())).thenReturn(userDto);

    ResponseEntity<UserDto> response = userController.update(ID, userDto);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDto.class, response.getBody().getClass());
  }

  @Test
  void whenDeleteThenReturnNoContent() {
    doNothing().when(userService).delete(anyInt());

    ResponseEntity<Void> response = userController.delete(ID);

    assertNotNull(response);
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(userService, times(1)).delete(anyInt());
  }
}