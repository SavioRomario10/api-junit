package io.savioromario10.api.service.impl;

import java.util.Optional;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.repository.UserRepository;
import io.savioromario10.api.service.UserService;
import io.savioromario10.api.service.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired  
  private UserRepository userRepository;

  @Override
  public User findById(Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new ObjectNotFoundException("User not found"));
  }
}