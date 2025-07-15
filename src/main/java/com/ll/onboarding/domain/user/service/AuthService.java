package com.ll.onboarding.domain.user.service;

import com.ll.onboarding.domain.user.dto.SignupRequest;
import com.ll.onboarding.domain.user.dto.UserResponse;
import com.ll.onboarding.domain.user.entity.User;
import com.ll.onboarding.domain.user.repository.UserRepository;
import com.ll.onboarding.global.enums.UserRole;
import com.ll.onboarding.global.error.ErrorCode;
import com.ll.onboarding.global.exception.CustomException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public UserResponse signup(@Valid SignupRequest req) {
    if (userRepository.findByEmail(req.getEmail()).isPresent()) {
      throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
    }

    User user = User.builder()
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword()))
        .name(req.getName())
        .role(UserRole.MEMBER)
        .build();

    User savedUser = userRepository.save(user);

    String token = jwtService.genToken(savedUser);

    return UserResponse.builder()
        .token(token)
        .name(savedUser.getName())
        .build();
  }

  public UserResponse login(@Valid SignupRequest req) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              req.getEmail(), req.getPassword()
          )
      );

      User user = userRepository.findByEmail(req.getEmail())
          .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

      String token = jwtService.genToken(user);

      return UserResponse.builder()
          .token(token)
          .name(user.getName())
          .build();

    } catch (BadCredentialsException e) {
      throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
    } catch (UsernameNotFoundException e) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
  }
}
