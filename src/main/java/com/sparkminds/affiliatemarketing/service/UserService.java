package com.sparkminds.affiliatemarketing.service;

import com.sparkminds.affiliatemarketing.dto.response.UserResponseDto;

public interface UserService {

  UserResponseDto getUserInfomationByEmail(String email);

  String getNameByRefferalCode(String refferalCode);
}
