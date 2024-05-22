package com.sparkminds.affiliatemarketing.service.imp;

import com.sparkminds.affiliatemarketing.constance.Constance;
import com.sparkminds.affiliatemarketing.dto.request.RegisterDto;
import com.sparkminds.affiliatemarketing.entity.User;
import com.sparkminds.affiliatemarketing.repository.UserRepository;
import com.sparkminds.affiliatemarketing.service.AuthService;
import com.sparkminds.affiliatemarketing.service.JwtService;
import com.sparkminds.affiliatemarketing.service.LinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private LinkService linkService;


  @Transactional
  public void saveUser(RegisterDto registerDto) {
    User user = User.builder().email(registerDto.getEmail())
        .password(registerDto.getPassword())
        .email(registerDto.getEmail())
        .name(registerDto.getName())
        .reward(Constance.initReward).build();
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    String refferalCode = linkService.generateRefferalCode();
    while (userRepository.existsByRefferalCode(refferalCode)) {
      refferalCode = linkService.generateRefferalCode();
    }
    user.setRefferalCode(refferalCode);
    userRepository.save(user);
  }

  @Override
  public boolean checkValidRegisterAccount(RegisterDto registerDto) {
    if (!registerDto.getPassword().equals(registerDto.getRePassword())) {
      return false;
    }
    User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
    return user == null ? true : false;
  }

  public Integer getUserIdByRefferalCode(String refferalCode) {
    return userRepository.findIdByRefferalCode(refferalCode)
        .orElseThrow(() -> new NullPointerException("User not found"));
  }

  @Override
  public String getUsernameFromToken(String token) {
    return jwtService.getUserNameFromToken(token);
  }

  public String generateToken(String username) {
    return jwtService.generateToken(username);
  }

  public void validateToken(String token) {
    jwtService.validateToken(token);
  }
}
