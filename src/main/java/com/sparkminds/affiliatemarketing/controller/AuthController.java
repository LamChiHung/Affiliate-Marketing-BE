package com.sparkminds.affiliatemarketing.controller;

import com.sparkminds.affiliatemarketing.constance.Constance;
import com.sparkminds.affiliatemarketing.dto.ResponseDto;
import com.sparkminds.affiliatemarketing.dto.request.LoginDto;
import com.sparkminds.affiliatemarketing.dto.request.RegisterDto;
import com.sparkminds.affiliatemarketing.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;
  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<?> addNewUser(@Valid @RequestBody RegisterDto user) {
    if (authService.checkValidRegisterAccount(user)) {
      authService.saveUser(user);
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.successStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    } else {
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.errorStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> getToken(@Valid @RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    if (authentication.isAuthenticated()) {
      String token = authService.generateToken(loginDto.getEmail());
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.successStatusCode)
          .data(token).build();
      return ResponseEntity.ok().body(response);
    } else {
      ResponseDto<?> response = ResponseDto.builder()
          .statusCode(Constance.errorStatusCode)
          .build();
      return ResponseEntity.ok().body(response);
    }
  }

}
