package com.sparkminds.affiliatemarketing.service.imp;

import com.sparkminds.affiliatemarketing.dto.response.UserResponseDto;
import com.sparkminds.affiliatemarketing.entity.User;
import com.sparkminds.affiliatemarketing.repository.UserRepository;
import com.sparkminds.affiliatemarketing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

  @Autowired
  UserRepository userRepository;

  public UserResponseDto getUserInfomationByEmail(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NullPointerException("User not found"));
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setEmail(user.getEmail());
    userResponseDto.setName(user.getName());
    userResponseDto.setReward(user.getReward());
    return userResponseDto;
  }

  public String getNameByRefferalCode(String refferalCode) {
    User user = userRepository.findByRefferalCode(refferalCode)
        .orElseThrow(() -> new NullPointerException("User not found"));
    return user.getName();
  }


}
