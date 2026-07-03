package io.savioromario10.api.service;

import io.savioromario10.api.domain.User;

public interface UserService {
  User findById(Integer id);
}
