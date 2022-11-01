package com.example.controllers;

import com.example.payload.response.MessageResponse;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/access")
public class AccessController {
	@PostMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> userAccess(@RequestHeader("Authorization") String header) {
		return ResponseEntity.ok(new MessageResponse("user access"));
	}

	@PostMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> moderatorAccess(@RequestHeader("Authorization") String header) {
		return ResponseEntity.ok(new MessageResponse("mod access"));
	}

	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminAccess(@RequestHeader("Authorization") String header) {
		return ResponseEntity.ok(new MessageResponse("admin access"));
	}
}
