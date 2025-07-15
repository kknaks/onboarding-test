package com.ll.onboarding.domain.chat.controller;

import com.ll.onboarding.domain.chat.dto.ChatRequest;
import com.ll.onboarding.domain.chat.dto.ChatResponse;
import com.ll.onboarding.domain.chat.dto.ChatThreadResponse;
import com.ll.onboarding.domain.chat.service.ChatService;
import com.ll.onboarding.domain.user.entity.User;
import com.ll.onboarding.global.globalDto.GlobalResponse;
import com.ll.onboarding.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ApiV1ChatController {

  private final ChatService chatService;

  @PostMapping
  public GlobalResponse<ChatResponse> createChat(
      @Valid @RequestBody ChatRequest request,
      @LoginUser User user) {

    ChatResponse response = chatService.createChat(user, request);
    return GlobalResponse.success(response);
  }

  @GetMapping
  public GlobalResponse<Page<ChatThreadResponse>> getChats(
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable,
      @LoginUser User user) {

    Page<ChatThreadResponse> threads = chatService.getChatsByUser(user, pageable);
    return GlobalResponse.success(threads);
  }

  @DeleteMapping("/threads/{threadId}")
  public GlobalResponse<Void> deleteThread(
      @PathVariable Long threadId,
      @LoginUser User user) {

    chatService.deleteChatThread(threadId, user);
    return GlobalResponse.success(null);
  }
}