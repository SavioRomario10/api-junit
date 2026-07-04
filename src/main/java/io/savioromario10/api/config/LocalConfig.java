package io.savioromario10.api.config;

import io.savioromario10.api.domain.User;
import io.savioromario10.api.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalConfig {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public CommandLineRunner startDB(){

    return args -> {
      User u1 = new User(
      null, "Savio", "savio@example.com", "12345");

      User u2 = new User(
      null, "Gaby","gaby@example.com", "12345");

      userRepository.saveAll(List.of(u1, u2));
    };
  }
}