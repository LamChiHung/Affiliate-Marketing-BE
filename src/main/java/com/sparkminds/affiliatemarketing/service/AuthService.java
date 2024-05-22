package com.sparkminds.affiliatemarketing.service;

import com.sparkminds.affiliatemarketing.dto.request.RegisterDto;

public interface AuthService {

  void saveUser(RegisterDto registerDto);

  boolean checkValidRegisterAccount(RegisterDto user);

  String generateToken(String username);

  void validateToken(String token);

  Integer getUserIdByRefferalCode(String refferalCode);

  String getUsernameFromToken(String token);

}
