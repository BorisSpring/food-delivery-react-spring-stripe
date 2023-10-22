package com.main.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.UserDTO;
import com.main.requests.CreateAccountRequest;
import com.main.requests.LoginRequest;
import com.main.responses.AuthResponse;
import com.main.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req){
		return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(req));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> registerHandler(@RequestBody CreateAccountRequest req){
		return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(req));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> findUserHandler(@RequestHeader("Authorization") String jwt){
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserFromToken(jwt));
	}
}
