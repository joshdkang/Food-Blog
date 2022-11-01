package com.example.service;

import java.util.Optional;

import com.example.models.User;
import com.example.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
	UserRepository userRepository;

	public User getAccountById(long id) {
		return userRepository.findById(id).get();
	}

  public User getAccountByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}
	
  public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

	public void saveOrUpdate(User account) {
		userRepository.save(account);
	}
	
	public void delete(long id) {
		userRepository.deleteById(id);
	}
}
