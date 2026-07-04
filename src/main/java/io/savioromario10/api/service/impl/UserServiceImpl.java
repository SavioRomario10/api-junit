package io.savioromario10.api.service.impl;

import java.util.List;
import java.util.Optional;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.domain.dto.UserDto;
import io.savioromario10.api.repository.UserRepository;
import io.savioromario10.api.service.UserService;
import io.savioromario10.api.service.exception.DataIntegratyViolationException;
import io.savioromario10.api.service.exception.ObjectNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired  
  private UserRepository userRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public User findById(Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("User not found"));
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User create(UserDto user) {
    validateEmail(user);
    return userRepository.save(mapper.map(user, User.class));
  }
  
  @Override
  public User update(UserDto user) {
    validateEmail(user);
    return userRepository.save(mapper.map(user, User.class));
  }
  
  @Override
  public void delete(Integer id) {
    findById(id);
    userRepository.deleteById(id);
  }

  private void validateEmail(UserDto user) {
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
    if (userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())) {
      throw new DataIntegratyViolationException("Email already registered");
    }
  }
}