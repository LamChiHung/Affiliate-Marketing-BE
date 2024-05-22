package com.sparkminds.affiliatemarketing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

  @Email
  @NotNull
  @Length(max = 100)
  private String email;
  @Length(max = 100)
  private String password;
}
