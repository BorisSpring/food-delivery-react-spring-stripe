package com.main.config;

import java.util.Collections;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	final class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {
		private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
		
			this.delegate.handle(request, response, csrfToken);
		}

		@Override
		public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
		
			if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) {
				return super.resolveCsrfTokenValue(request, csrfToken);
			}
		
			return this.delegate.resolveCsrfTokenValue(request, csrfToken);
		}
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		

		XorCsrfTokenRequestAttributeHandler xorHandler = new XorCsrfTokenRequestAttributeHandler();
		xorHandler.setCsrfRequestAttributeName("_csrf");
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.securityContext(context -> context.requireExplicitSave(false))
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.GET, "/api/categories", "/api/products" , "/api/products/image/{imageName}" , "/api/products/category/{categoryId}").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/signup", "/auth/signin", "/stripe-webhook").permitAll()
				
				.requestMatchers(HttpMethod.POST, "/api/categories", "/api/products").hasAuthority("ADMIN")

				.requestMatchers(HttpMethod.DELETE, "/api/categories/{categoryId}", "/api/orders/{orderId}", "/api/products/{productId}", "/api/users/{userId}").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/products", "/api/users/{userId}").hasAuthority("ADMIN")
				
				.requestMatchers(HttpMethod.GET, "/api/users", "/api/orders").hasAnyAuthority("ADMIN","EMPLOYEE")
				.requestMatchers(HttpMethod.POST , "/api/orders/orderStatus/{orderId}", "/api/products/enable/{productId}" , "/api/products/disable/{productId}").hasAnyAuthority("ADMIN", "EMPLOYEE")
				.anyRequest().authenticated()
				)
		.cors(customizer -> customizer.configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setMaxAge(86000L);
				config.setExposedHeaders(Collections.singletonList("Authorization"));
				config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173"));
				
				return config;
			}
		}))
		.csrf((csrf) -> csrf.csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()).ignoringRequestMatchers("/auth/signup", "/auth/signin", "/stripe-webhook")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
}
