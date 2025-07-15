package com.ll.onboarding.domain.chat.dto;

import com.ll.onboarding.domain.chat.entity.Chat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {
  private Long chatId;
  private Long threadId;
  private String question;
  private String answer;
  private LocalDateTime createdAt;

  public static ChatResponse from(Chat chat) {
    return ChatResponse.builder()
        .chatId(chat.getId())
        .threadId(chat.getChatThread().getId())
        .question(chat.getQuestion())
        .answer(chat.getAnswer())
        .createdAt(chat.getCreatedAt())
        .build();
  }
}