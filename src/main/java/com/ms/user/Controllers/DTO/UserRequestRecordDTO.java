package com.ms.user.Controllers.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Campos obrigatórios
public class UserRequestRecordDTO {
  @NotBlank 
  private String name; 
  
  @NotBlank 
  @Email 
  private String email; 
  
  @NotBlank 
  @Size(min = 8) 
  @Pattern(
    regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=*])[A-Za-z\\d@#$%^&+=*]{8,}$",
    message = "Password must contain at least one uppercase letter, one digit, one special character, and be at least 8 characters long"
  ) 
  private String password;

}
