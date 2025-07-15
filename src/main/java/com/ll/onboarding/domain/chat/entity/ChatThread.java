package com.ll.onboarding.domain.chat.entity;

import com.ll.onboarding.domain.user.entity.User;
import com.ll.onboarding.global.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "threads")
public class ChatThread extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToMany(mappedBy = "chatThread", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Chat> chats = new ArrayList<>();

  public boolean isExpired() {
    return getModifiedAt().isBefore(LocalDateTime.now().minusMinutes(30));
  }

  public Chat addChat(String question, String answer) {
    Chat chat = Chat.builder()
        .chatThread(this)
        .question(question)
        .answer(answer)
        .build();
    chats.add(chat);
    return chat;
  }

  public boolean canBeDeletedBy(Long userId) {
    return this.user.getId().equals(userId);
  }


}
