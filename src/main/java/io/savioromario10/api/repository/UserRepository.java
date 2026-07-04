package io.savioromario10.api.repository;

import io.savioromario10.api.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

  Optional<User> findByEmail(String email);
}