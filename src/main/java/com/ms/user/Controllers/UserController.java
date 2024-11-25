package com.ms.user.Controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.Controllers.DTO.UserRequestRecordDTO;
import com.ms.user.Controllers.DTO.UserResponseRecordDTO;
import com.ms.user.Entities.UserModel;
import com.ms.user.Exceptions.UserAlreadyExistsException;
import com.ms.user.Services.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

  // Injeção de dependência
  final UserService userService;

  // Construtor
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // Método POST para criar um usuário
  @PostMapping("/users")
  public ResponseEntity<Object> createUser(
      @RequestBody @Valid UserRequestRecordDTO userRecordRequestDTO, BindingResult bindingResult) {

    // Verifica se houve erros de validação
    if (bindingResult.hasErrors()) {
      StringBuilder errorMessage = new StringBuilder("Validation errors: ");
      
      // Itera sobre os erros de validação e cria uma mensagem personalizada
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorMessage.append("Field: ").append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage()).append("; ");
      }
      // Retorna os erros de validação com um status BAD_REQUEST
      return ResponseEntity.badRequest().body(errorMessage.toString());
    }

    try {
      // Cria um objeto UserModel a partir do DTO
      UserModel user = new UserModel();
      BeanUtils.copyProperties(userRecordRequestDTO, user);
      UserResponseRecordDTO result = userService.execute(user);
      
      // Retorna uma resposta de sucesso se o usuário foi criado com sucesso
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (HttpMessageNotReadableException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
    } catch (UserAlreadyExistsException e) {
      // Retorna um erro de conflito se o usuário já existe, com a mensagem diretamente na resposta
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }

}
