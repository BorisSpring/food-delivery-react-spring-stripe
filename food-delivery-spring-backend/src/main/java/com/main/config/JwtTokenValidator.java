package com.main.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		try {
		
			String jwt = request.getHeader("Authorization");
			if(jwt != null) {	
				jwt = jwt.substring(7);
				try {
					SecretKey key = Keys.hmacShaKeyFor(JwtConst.KEY.getBytes(StandardCharsets.UTF_8));
					
					Claims claims = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(jwt)
							.getBody();
					
					String email = (String) claims.get("email");
					String authorities = String.valueOf(claims.get("authorities")).toUpperCase();
					
					Authentication auth = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				
					
					SecurityContextHolder.getContext().setAuthentication(auth);
				} catch (Exception e) {
					throw new BadCredentialsException("Invalid token received1");
				}
			}else {
				throw new BadCredentialsException("Invalid token received2");
			}
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid token received 3" + request.getMethod() +  " "  + request.getServletPath());
		}
		
			
			filterChain.doFilter(request, response);
	}

	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		List<String> pathsToExclude = new ArrayList<>();
	    pathsToExclude.add("/auth/signin:POST");
	    pathsToExclude.add("/auth/signup:POST");
	    pathsToExclude.add("/stripe-webhook:POST");
	    pathsToExclude.add("/api/categories:GET");
	    pathsToExclude.add("/api/products:GET");
		
				
		return pathsToExclude.contains(request.getServletPath() + ":" + request.getMethod()) || (request.getServletPath().startsWith("/api/products/image") && request.getMethod().equals("GET")) || (request.getServletPath().startsWith("/api/products/category/") && request.getMethod().toUpperCase() =="GET");
	}
	
	
}
