package com.ll.onboarding.domain.chat.service;

import com.ll.onboarding.domain.chat.dto.ChatRequest;
import com.ll.onboarding.domain.chat.dto.ChatResponse;
import com.ll.onboarding.domain.chat.dto.ChatThreadResponse;
import com.ll.onboarding.domain.chat.entity.Chat;
import com.ll.onboarding.domain.chat.entity.ChatThread;
import com.ll.onboarding.domain.chat.repository.ChatRepository;
import com.ll.onboarding.domain.chat.repository.ChatThreadRepository;
import com.ll.onboarding.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

  private final ChatRepository chatRepository;
  private final ChatThreadRepository chatThreadRepository;

  public ChatResponse createChat(User user, ChatRequest request) {
    // 1. 스레드 찾기 또는 생성
    ChatThread chatThread = findOrCreateChatThread(user);

    // 2. Mock 답변 생성 (OpenAI 대신)
    String answer = "안녕하세요! '" + request.getQuestion() + "'에 대한 답변입니다. (Mock 응답)";

    // 3. 채팅 생성 및 저장
    Chat chat = chatThread.addChat(request.getQuestion(), answer);
    chatThreadRepository.save(chatThread);

    return ChatResponse.from(chat);
  }

  @Transactional(readOnly = true)
  public Page<ChatThreadResponse> getChatsByUser(User user, Pageable pageable) {
    Page<ChatThread> chatThreads = chatThreadRepository.findByUser(user, pageable);
    return chatThreads.map(ChatThreadResponse::from);
  }

  public void deleteChatThread(Long chatThreadId, User user) {
    ChatThread chatThread = chatThreadRepository.findById(chatThreadId)
        .orElseThrow(() -> new IllegalArgumentException("스레드를 찾을 수 없습니다"));

    if (!chatThread.canBeDeletedBy(user.getId())) {
      throw new IllegalArgumentException("삭제 권한이 없습니다");
    }

    chatThreadRepository.delete(chatThread);
  }

  private ChatThread findOrCreateChatThread(User user) {
    return chatThreadRepository.findLatestByUser(user)
        .filter(chatThread -> !chatThread.isExpired())
        .orElseGet(() -> {
          ChatThread newChatThread = ChatThread.builder()
              .user(user)
              .build();
          return chatThreadRepository.save(newChatThread);
        });
  }
}