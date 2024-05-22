package com.sparkminds.affiliatemarketing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {

  @Email
  @NotNull
  @Length(max = 100)
  private String email;
  @Length(min = 2, max = 100)
  @NotBlank
  private String name;
  @Length(min = 8, max = 100)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d$@$!%*?&]).*$")
  private String password;
  @Length(min = 8, max = 100)
  private String rePassword;
}
