package io.savioromario10.api.service.impl;

import java.util.Optional;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.repository.UserRepository;
import io.savioromario10.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

  @Autowired  
  private UserRepository userRepository;

  @Override
  public User findById(Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElse(null);
  }
}