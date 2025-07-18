package com.ll.onboarding.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
  @Email(message = "이메일 형식이 올바르지 않습니다")
  @NotBlank(message = "이메일은 필수입니다")
  private String email;

  @NotBlank(message = "비밀번호는 필수입니다")
  @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
  private String password;

  @NotBlank(message = "이름은 필수입니다")
  @Size(max = 100, message = "이름은 100자를 초과할 수 없습니다")
  private String name;
}