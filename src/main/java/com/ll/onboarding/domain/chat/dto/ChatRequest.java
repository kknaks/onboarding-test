package com.ll.onboarding.domain.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
  @NotBlank(message = "질문은 필수입니다")
  private String question;
  private boolean isStreaming;
  private String model;
}