package com.main.controllers;


import com.main.dto.UserDto;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.main.service.UserService;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@RequiredArgsConstructor
@Validated
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserDto>> findAllUsershandler(@Positive(message = "Page number must be positive") @RequestParam(name ="pageNumber", required = false, defaultValue = "1") Integer pageNumber,
															 @Positive(message = "Page size must be positive") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		return ResponseEntity.ok(userService.findAllUsers(pageNumber, pageSize));
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void enableDisableUserHandler(@Positive(message = "User id must be positive number!") @RequestParam int userId){
		userService.enableDisableUser(userId);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUserHadnler(@Positive(message = "User id must be positive number!") @RequestParam int userId){
    	 userService.deleteUser(userId);
	}
}
