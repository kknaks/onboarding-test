package com.ll.onboarding.domain.chat.repository;

import com.ll.onboarding.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
