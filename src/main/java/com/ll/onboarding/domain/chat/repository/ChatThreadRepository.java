package com.ll.onboarding.domain.chat.repository;

import com.ll.onboarding.domain.chat.entity.ChatThread;
import com.ll.onboarding.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatThreadRepository extends JpaRepository<ChatThread, Long> {

  Page<ChatThread> findByUser(User user, Pageable pageable);

  @Query("SELECT ct FROM ChatThread ct WHERE ct.user = :user ORDER BY ct.modifiedAt DESC LIMIT 1")
  Optional<ChatThread> findLatestByUser(@Param("user") User user);
}
