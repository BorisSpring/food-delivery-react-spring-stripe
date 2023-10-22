package com.main.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.entity.User;
import com.main.repository.UserRepository;

@Service
public class LoadUserService implements UserDetailsService{

	
	private UserRepository userRepo;
	

	public UserRepository getUserRepo() {
		return userRepo;
	}


	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		User user = userRepo.findByEmail(username);
		
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(), 
            Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().getName()))
        );
		
		return userDetails;
	}

}
