package com.sparkminds.affiliatemarketing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkDto {

  @Length(min = 2, max = 100)
  @NotBlank
  @Pattern(regexp = "^[a-zA-z0-9 ]+$")
  private String productName;
}
