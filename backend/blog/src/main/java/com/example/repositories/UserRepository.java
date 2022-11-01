package com.example.repositories;

import com.example.models.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);
  Optional<User> findByUsername(String username);
  Optional<User> findById(Long id);
}
