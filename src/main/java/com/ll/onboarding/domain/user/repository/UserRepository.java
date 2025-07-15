package com.ll.onboarding.domain.user.repository;

import com.ll.onboarding.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String username);
}
