package com.ll.onboarding.domain.chat.dto;

import com.ll.onboarding.domain.chat.entity.ChatThread;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatThreadResponse {
  private Long threadId;
  private LocalDateTime createdAt;
  private LocalDateTime lastActivity;
  private List<ChatResponse> chats;

  public static ChatThreadResponse from(ChatThread chatThread) {
    return ChatThreadResponse.builder()
        .threadId(chatThread.getId())
        .createdAt(chatThread.getCreatedAt())
        .lastActivity(chatThread.getModifiedAt())
        .chats(chatThread.getChats().stream()
            .map(ChatResponse::from)
            .toList())
        .build();
  }
}
