package com.main.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.config.JwtConst;
import com.main.dto.UserDTO;
import com.main.entity.User;
import com.main.exceptions.UserException;
import com.main.repository.AuthorityRepository;
import com.main.repository.UserRepository;
import com.main.requests.CreateAccountRequest;
import com.main.requests.LoginRequest;
import com.main.responses.AuthResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;
	private AuthorityRepository authorityRepo;
	private PasswordEncoder passwordEncoder;

	
	
	public UserServiceImpl(UserRepository userRepo, AuthorityRepository authorityRepo,
			PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.authorityRepo = authorityRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<User> findAllUsers() {
		 return userRepo.findAll();
	}

	
	
	@Override
	public String getEmailFromToken(String jwt) {
		try {
			jwt = jwt.substring(7);
			SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));
			
			Claims claim = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
			
			return (String) claim.get("email");
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token received1");
		}
	}
	
	
	
	
	@Override
	public AuthResponse loginUser(LoginRequest req) {
		
		User user = userRepo.findByEmail(req.getEmail());
		
		if(user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) 
			throw new BadCredentialsException("Invalid Provided Credentials");
		
		return new AuthResponse(true, generateToken(user.getEmail(), Collections.singletonList(user.getAuthority().getName())));
	}

	
	
	@Transactional
	@Override
	public AuthResponse registerUser(CreateAccountRequest req) {
			
		if(!req.getPassword().equals(req.getRepeatedPassword()))
			throw new UserException("Passwords Must match");

		User user = userRepo.findByEmail(req.getEmail());
		
		if(user != null)
			throw new UserException("Email alerdy in use");
		
		user = new User();
		user.setEmail(req.getEmail());
		user.setEnabled(true);
		user.setCreated(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setAuthority(authorityRepo.findByName(req.getAuthority()));
		user.setFirstName(req.getFirstName());
		user.setLastName(req.getLastName());
		user = userRepo.save(user);
		
		if(user == null)
			throw new UserException("Fail to register account!");
		
		return new AuthResponse(true,generateToken(req.getEmail(), Collections.singletonList(user.getAuthority().getName())));
	}

	private String generateToken(String email, List<String> authorities) {
		
		try {
			
			SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));
			
			String jwt = Jwts.builder()
					.setIssuer("Boris Dimitrijevic")
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + 86400000))
					.claim("email" , email)
					.claim("authorities", String.join(",", authorities))
					.signWith(key)
					.compact();
				return jwt;	
			
		} catch (Exception e) {
			throw new BadCredentialsException("Fail to generate token");
		}
	}
	
	@Override
	public UserDTO getUserFromToken(String jwt) {
			
		try {
			jwt = jwt.substring(7);
			SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));
			
			Claims claim = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
		
			User user = userRepo.findByEmail((String)claim.get("email"));
			
			if(user == null)
				throw new BadCredentialsException("User not found");
			
			UserDTO dto = new UserDTO();
			
			dto.setAuthority(user.getAuthority().getName());
			dto.setId(user.getId());
			dto.setEmail(user.getEmail());
			dto.setOrders(user.getOrders());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			
			return dto;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BadCredentialsException("Invalid token received");
		}
	}

	@Transactional
	@Override
	public boolean enableDisableUser(int userId) throws UserException {
			
		if(!userRepo.existsById(userId))
			throw new UserException("user with id " + userId + " doesnt exists");
		
		User user = userRepo.findById(userId).get();
		
		user.setEnabled(user.isEnabled() ? false : true);
		user = userRepo.save(user);
		
		if(user == null)
			throw new UserException("Fail to update user statsu");
		
		return true;
	}

	@Transactional
	@Override
	public boolean deleteUser(int userId) throws UserException {
		
		if(!userRepo.existsById(userId))
			throw new UserException("user with id " + userId + " doesnt exists");
		
		userRepo.deleteById(userId);
		
		return true;
		
	}

	@Override
	public User findById(int userId) throws UserException {
		Optional<User> opt = userRepo.findById(userId);
		
		if(!opt.isPresent())
			throw new UserException("User not found");
		
		return opt.get();
	}

	@Override
	public User findByEmail(String email) throws UserException {
		
		User user = userRepo.findByEmail(email);
		
		if(user == null)
			throw new UserException("User not found!");
		
		return user;
	}
}
