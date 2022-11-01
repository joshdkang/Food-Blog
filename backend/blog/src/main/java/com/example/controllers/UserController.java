package com.example.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.models.User;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
  @Autowired
	UserService userService;

  @PostMapping("/register")
	@ResponseBody
	public ResponseEntity<Map<String, String>> createAccount(@RequestBody User user) {	
    Map<String, String> map = new HashMap<String, String>();

    try {
      if(userService.existsByUsername(user.getUsername())) throw new Exception();

      userService.saveOrUpdate(user);
      map.put("accountId", user.getId().toString());
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
    
	}

  @PostMapping("/login")
  @ResponseBody
  private ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> payload) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      User user = userService.getAccountByUsername(payload.get("username"));

      if (!payload.get("password").equals(user.getPassword()))
        throw new Exception("Invalid password");
      
      map.put("accountId", user.getId().toString());
      map.put("username", user.getUsername().toString());
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
  }

  // get profile info (get request)

  // change description post request

  // change profile picture post request
}
