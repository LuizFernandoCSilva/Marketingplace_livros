package com.ms.user.Services;

import org.springframework.stereotype.Service;

import com.ms.user.Controllers.DTO.UserRequestRecordDTO;
import com.ms.user.Controllers.DTO.UserResponseRecordDTO;
import com.ms.user.Entities.UserModel;
import com.ms.user.Repositories.UserRepository;
import com.ms.user.Exceptions.UserAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;

@Service
public class UserService {

  // Injeção de dependência do repositório
  final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Método para salvar um usuário
  @Transactional
  public UserResponseRecordDTO execute(UserModel user) {
    // Verifica se o usuário já existe
    userRepository.findByEmail(user.getEmail())
      .ifPresent(existingUser -> {
        throw new UserAlreadyExistsException("User already exists with this email");
      });

    // Criptografa a senha do usuário
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    UserModel savedUser = userRepository.save(user);
    // Salva o usuário
    return UserResponseRecordDTO.builder()
      .name(savedUser.getName())
      .email(savedUser.getEmail())
      .build();
  }
}
