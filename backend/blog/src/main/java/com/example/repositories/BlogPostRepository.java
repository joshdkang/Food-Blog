package com.example.repositories;

import java.util.Optional;

import com.example.models.User;
import com.example.models.BlogPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long>  {
  Optional<BlogPost> findById(Long id);
  List<BlogPost> findAllByUser(User user);
  Page<BlogPost> findAllByUser(User user, Pageable page);
  List<BlogPost> findAllByTitleContainingIgnoreCase(String title, Pageable page);
  Page<BlogPost> findAllByTitleContainingOrDescriptionContainingOrContentContainingIgnoreCase(String term, String term2, String term3, Pageable page);
}
