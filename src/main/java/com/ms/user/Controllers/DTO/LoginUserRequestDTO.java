package com.ms.user.Controllers.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequestDTO {
  @NotBlank
  private String email;
  
  @NotBlank
  private String password;
}
