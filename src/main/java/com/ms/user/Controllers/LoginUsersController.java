package com.ms.user.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.Controllers.DTO.LoginUserRequestDTO;
import com.ms.user.Services.LoginUsersService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class LoginUsersController {


  final LoginUsersService loginCandidateService;

  public LoginUsersController(LoginUsersService loginCandidateService) {
    this.loginCandidateService = loginCandidateService;
  }
  
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginUserRequestDTO userLogin) {
   try{
    var token = this.loginCandidateService.execute(userLogin);
    return ResponseEntity.ok().body(token);
   }catch (Exception e) {
     return ResponseEntity.status(500).body(e.getMessage());
   }
  }
}
