package com.main.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import com.main.exceptions.ResourceNotFoundException;
import com.main.mappers.UserMapper;
import com.main.requests.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.config.JwtConst;
import com.main.dto.UserDto;
import com.main.entity.User;
import com.main.exceptions.UserException;
import com.main.repository.AuthorityRepository;
import com.main.repository.UserRepository;
import com.main.requests.LoginRequest;
import com.main.responses.AuthResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepo;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Override
	public Page<UserDto> findAllUsers(Integer pageNumber, Integer pageSize) {
		return userRepository.findAll(PageRequest.of((pageNumber - 1), pageSize)).map(userMapper::userToUserDto);
	}

	@Override
	public String getEmailFromToken(String jwt) {
		return getClaimsFromToken(jwt).get("email").toString();
	}

	@Override
	public Integer getUserIdFromToken(String jwt) {
		return (Integer) getClaimsFromToken(jwt).get("userId");
	}


	@Override
	public AuthResponse loginUser(LoginRequest req) {

		User user =  userRepository.findByEmail(req.getEmail())
							.orElseThrow(() -> new BadCredentialsException("Invalid credentials!"));

		if(!passwordEncoder.matches(req.getPassword(), user.getPassword()))
			throw new BadCredentialsException("Invalid Provided Credentials");
		
		return AuthResponse.builder()
						.isAuth(true)
						.jwt(generateToken(user))
						.build();
	}

	
	
	@Transactional
	@Override
	public AuthResponse registerUser(UserRegistrationRequest req) {
			
		if(!req.getPassword().equals(req.getRepeatedPassword()))
			throw new UserException("Passwords Must match");

		userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
			throw new UserException("There is already a user with the same email address!");
		});

		User user = userRepository.save(User.builder()
				.email(req.getEmail())
				.enabled(true)
				.password(req.getPassword())
				.authority(authorityRepo.findByName("user"))
				.firstName(req.getFirstName())
				.lastName(req.getLastName())
				.build());

		return AuthResponse.builder()
				.isAuth(true)
				.jwt(generateToken(user))
				.build();
	}

	private String generateToken(User user) {
		
		try {
			SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));
			 return Jwts.builder()
						.setIssuer("Boris Dimitrijevic")
						.setIssuedAt(new Date())
						.setExpiration(new Date(new Date().getTime() + 86400000))
						.claim("email" , user.getEmail())
						.claim("authorities", user.getAuthority().getName())
					 	.claim("userId", user.getId())
						.signWith(key)
						.compact();
		} catch (Exception e) {
			throw new BadCredentialsException("Fail to generate token");
		}
	}
	public Claims getClaimsFromToken(String jwt){
		try{
			jwt = jwt.substring(7);
			SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));

			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
		}catch (Exception e){
			throw new BadCredentialsException("Invalid token recived!");
		}
	}
	@Override
	public UserDto getUserFromToken(String jwt) {
			
		try {
			User user = userRepository.findByEmail((String) getClaimsFromToken(jwt).get("email"))
							.orElseThrow(() -> new ResourceNotFoundException("Invalid token received!"));
			
			return   UserDto.builder()
							.authority(user.getAuthority().getName())
							.id(user.getId())
							.imageName(user.getImageName())
							.email(user.getEmail())
							.orders(user.getOrders())
							.firstName(user.getFirstName())
							.lastName(user.getLastName())
							.build();
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token received");
		}
	}

	@Transactional
	@Override
	public void enableDisableUser(int userId) throws UserException {
		User user = findById(userId);

		user.setEnabled(!user.isEnabled());
		userRepository.save(user);
	}

	@Transactional
	@Override
	public void deleteUser(int userId) throws UserException {
		if(!userRepository.existsById(userId))
			throw new UserException("user with id " + userId + " doesnt exists");
		
		userRepository.deleteById(userId);
	}

	@Override
	public User findById(int userId) throws UserException {
		return  userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found!"));
	}

	@Override
	public User findByEmail(String email) throws UserException {
		return  userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found!"));
	}
}
