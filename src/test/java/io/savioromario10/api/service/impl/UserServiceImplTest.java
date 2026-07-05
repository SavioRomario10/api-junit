package io.savioromario10.api.service.impl;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.domain.dto.UserDto;
import io.savioromario10.api.repository.UserRepository;
import io.savioromario10.api.service.exception.DataIntegratyViolationException;
import io.savioromario10.api.service.exception.ObjectNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

  private static final Integer ID = 1;
  private static final String NAME = "Savio";
  private static final String EMAIL = "mario@sv.com";
  private static final String PASSWORD = "12345678";

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private ModelMapper mapper;
  
  private User user;
  private UserDto userDto;
  private Optional<User> optionalUser;

  @BeforeEach
  void setUp(){
    openMocks(this);
    startUser();
  }
  private void startUser(){
    user = new User(ID, NAME, EMAIL, PASSWORD);
    userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
  }
  
  @Test
  void findByIdThenReturnAnUserInstance() {
    when(userRepository.findById(anyInt())).thenReturn(optionalUser);

    User foundUser = userService.findById(ID);

    assertNotNull(foundUser);
    assertEquals(User.class, foundUser.getClass());
    assertEquals(ID, foundUser.getId());
  }

  @Test
  void findByIdThenReturnAnObjectNotFoundException() {
    when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException("User not found"));

    try{
      userService.findById(ID);
    } catch (Exception ex){
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals("User not found", ex.getMessage());
    }
  }

  @Test
  void findAllThenReturnAnListOfUsers() {
    when(userRepository.findAll()).thenReturn(List.of(user));

    List<User> users = userService.findAll();
    
    assertNotNull(users);

    assertEquals(1, users.size());
    assertEquals(User.class, users.get(0).getClass());
    assertEquals(ID, users.get(0).getId());
  }

  @Test
  void createThenReturnAnUserInstance() {
    when(userRepository.save(any())).thenReturn(user);

    User createdUser = userService.create(userDto);

    assertNotNull(createdUser);
    assertEquals(User.class, createdUser.getClass());
    assertEquals(ID, createdUser.getId());
  }

  @Test
  void whenCreateThenReturnException(){
    when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

    try{
      userService.create(userDto);
    }catch (Exception ex){
      assertEquals(DataIntegratyViolationException.class, ex.getClass());
      assertEquals("Email already registered", ex.getMessage());
    }
  }

  @Test
  void updateThenReturnAnUserInstance() {
    when(userRepository.save(any())).thenReturn(user);

    User updatedUser = userService.update(userDto);

    assertNotNull(updatedUser);
    assertEquals(User.class, updatedUser.getClass());
    assertEquals(ID, updatedUser.getId());
  }

  @Test
  void whenUpdateThenReturnException(){
    when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

    try{
      userService.update(userDto);
    }catch (Exception ex){
      assertEquals(DataIntegratyViolationException.class, ex.getClass());
      assertEquals("Email already registered", ex.getMessage());
    }
  }

  @Test
  void deleteThenReturnVoid() {
  
    when(userRepository.findById(anyInt())).thenReturn(optionalUser);
    doNothing().when(userRepository).deleteById(anyInt());
    
    userService.delete(ID);

    verify(userRepository, times(1)).deleteById(anyInt());
  }

  @Test
  void deleteThenReturnException(){
    when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException("User not found"));

    try{
      userService.delete(ID);
    }catch (Exception ex){
      assertEquals(ObjectNotFoundException.class, ex.getClass());
    }
  }
}