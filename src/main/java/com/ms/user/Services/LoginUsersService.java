package com.ms.user.Services;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.ms.user.Controllers.DTO.AuthUserResponseDTO;
import com.ms.user.Controllers.DTO.LoginUserRequestDTO;
import com.ms.user.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LoginUsersService {

  @Value("${jwt.secret}")
  private String secretKey;

  final UserRepository userRepository;
  final PasswordEncoder passwordEncoder;

  public LoginUsersService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Transactional
  public AuthUserResponseDTO execute(LoginUserRequestDTO userLogin) {
    var userOptional = this.userRepository.findByEmail(userLogin.getEmail());
    if (userOptional.isEmpty()) {
      throw new RuntimeException("User not found");
    }
    var user = userOptional.get();
    var passwordMatch = passwordEncoder.matches(userLogin.getPassword(), user.getPassword());
  
    if (!passwordMatch) {
      throw new RuntimeException("Email/Password Invalid");
    }
    Algorithm algorithm = Algorithm.HMAC256(secretKey); 
    var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
    var token = JWT.create()
      .withSubject(user.getUserId().toString())
      .withExpiresAt(java.util.Date.from(expiresIn))
      .sign(algorithm);

    return AuthUserResponseDTO.builder()
            .token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();
  }
}
