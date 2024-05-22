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

  @Length(min = 2, max = 100, message = "The length of product name must be in 2 - 100 characters")
  @NotBlank(message = "product name can't blank")
  @Pattern(regexp = "^[a-zA-z0-9 ]+$", message = "product name can't contain special characters")
  private String productName;
}
