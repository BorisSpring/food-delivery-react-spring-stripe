package com.main.service;

import java.util.List;

import com.main.dto.UserDto;
import com.main.entity.User;
import com.main.exceptions.UserException;
import com.main.requests.LoginRequest;
import com.main.requests.UserRegistrationRequest;
import com.main.responses.AuthResponse;
import org.springframework.data.domain.Page;

public interface UserService {

	 Page<UserDto> findAllUsers(Integer pageNumber, Integer pageSize);
	
	 AuthResponse loginUser(LoginRequest req);
	
	 AuthResponse registerUser(UserRegistrationRequest req);
	
	 UserDto getUserFromToken(String jwt);
	
	 void enableDisableUser(int userId) throws UserException;
	
	 void deleteUser(int userId) throws UserException;
	
	 User findById(int userId) throws UserException;

	String getEmailFromToken(String jwt);
	Integer getUserIdFromToken(String jwt);
  	User findByEmail(String email) throws UserException;
}
