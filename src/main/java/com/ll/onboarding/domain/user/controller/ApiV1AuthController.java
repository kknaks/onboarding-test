package com.ll.onboarding.domain.user.controller;

import com.ll.onboarding.domain.user.dto.UserResponse;
import com.ll.onboarding.domain.user.dto.SignupRequest;
import com.ll.onboarding.domain.user.service.AuthService;
import com.ll.onboarding.global.globalDto.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiV1AuthController {
  private final AuthService authService;

  @PostMapping("/signup")
  public GlobalResponse singup(@Valid @RequestBody SignupRequest req) {
    UserResponse res = authService.signup(req);
    return GlobalResponse.success(res);
  }

  @PostMapping("/login")
  public GlobalResponse login(@Valid @RequestBody SignupRequest req) {
    UserResponse res = authService.login(req);
    return GlobalResponse.success(res);
  }
}
