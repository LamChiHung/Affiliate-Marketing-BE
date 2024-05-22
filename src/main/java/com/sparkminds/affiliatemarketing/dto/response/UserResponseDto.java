package com.sparkminds.affiliatemarketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

  private String name;
  private String email;
  private Long reward;
}
