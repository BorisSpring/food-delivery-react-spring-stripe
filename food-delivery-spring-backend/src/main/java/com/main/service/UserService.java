package com.main.service;

import java.util.List;

import com.main.dto.UserDTO;
import com.main.entity.User;
import com.main.exceptions.UserException;
import com.main.requests.CreateAccountRequest;
import com.main.requests.LoginRequest;
import com.main.responses.AuthResponse;

public interface UserService {

	public List<User> findAllUsers();
	
	public AuthResponse loginUser(LoginRequest req);
	
	public AuthResponse registerUser(CreateAccountRequest req);
	
	public UserDTO getUserFromToken(String jwt);
	
	public boolean enableDisableUser(int userId) throws UserException;
	
	public boolean deleteUser(int userId) throws UserException;
	
	public User findById(int userId) throws UserException;

	String getEmailFromToken(String jwt);
	
	public User findByEmail(String email) throws UserException;
}
