package com.main.controllers;

import com.main.requests.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.main.dto.UserDto;
import com.main.requests.LoginRequest;
import com.main.responses.AuthResponse;
import com.main.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

	private final UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@Valid @RequestBody LoginRequest req){
		return ResponseEntity.ok(userService.loginUser(req));
	}
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> registerHandler(@Valid @RequestBody UserRegistrationRequest createAccountRequest){
		return ResponseEntity.ok(userService.registerUser(createAccountRequest));
	}
	
	@GetMapping
	public ResponseEntity<UserDto> findUserHandler(@RequestHeader("Authorization") String jwt){
		return ResponseEntity.ok(userService.getUserFromToken(jwt));
	}
}
