package io.savioromario10.api.service;

import java.util.List;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.domain.dto.UserDto;

public interface UserService {
  User findById(Integer id);
  List<User> findAll();
  User create(UserDto user);
}
