package com.main.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.securityContext(context -> context.requireExplicitSave(false))
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.GET, "/api/categories","/api/categories/products", "/api/products" , "/api/products/image/{imageName}" , "/api/products/category").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/signup", "/auth/signin", "/stripe-webhook").permitAll()
				
				.requestMatchers(HttpMethod.POST, "/api/categories", "/api/products").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/categories", "/api/orders", "/api/products", "/api/users").hasAnyAuthority("ADMIN", "admin")
				.requestMatchers(HttpMethod.DELETE, "/api/categories", "/api/orders", "/api/products", "/api/users").hasAnyRole("ADMIN", "admin")

				.requestMatchers(HttpMethod.GET, "/api/users", "/api/orders").hasAnyAuthority("ADMIN","EMPLOYEE")
				.requestMatchers(HttpMethod.PUT , "/api/orders/cancel","/api/orders/orderStatus", "/api/products/enable" , "/api/products/disable").hasAnyAuthority("ADMIN", "EMPLOYEE")
				.anyRequest().authenticated())
		.cors(customizer -> customizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setMaxAge(24*60*60*60L);
            config.setExposedHeaders(Collections.singletonList("Authorization"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173"));
            return config;
        }))
				.csrf(AbstractHttpConfigurer::disable)
//		.csrf((csrf) -> csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()).ignoringRequestMatchers("/auth/signup", "/auth/signin", "/stripe-webhook")
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
}
