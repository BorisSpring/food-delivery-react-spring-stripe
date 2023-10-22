package com.main.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.User;
import com.main.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	
	
	
	public UserController(UserService userService) {
		this.userService = userService;
	}



	@GetMapping
	public ResponseEntity<List<User>> findAllUsershandler(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<Boolean> enableDisableUserHandler(@PathVariable int userId){
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.enableDisableUser(userId));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Boolean> deleteUserHadnler(@PathVariable int userId){
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
	}
}
