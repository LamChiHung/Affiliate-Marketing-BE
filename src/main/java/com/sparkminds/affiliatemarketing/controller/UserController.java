package com.sparkminds.affiliatemarketing.controller;

import com.sparkminds.affiliatemarketing.dto.ResponseDto;
import com.sparkminds.affiliatemarketing.dto.response.UserResponseDto;
import com.sparkminds.affiliatemarketing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseEntity<?> getUser(Authentication authentication) {
    UserResponseDto userResponseDto = userService.getUserInfomationByEmail(
        authentication.getName());
    ResponseDto<?> response = ResponseDto.builder().data(userResponseDto).statusCode("00").build();
    return ResponseEntity.ok().body(response);
  }
}
