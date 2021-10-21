package com.example.demo.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//1st Filter.
public class JwtUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	
	//Autowiring AuthenticationManager.
	@Autowired
	public JwtUsernamePasswordAuthFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig,
			SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	@Override
	//Method that tries to authenticate.
	public Authentication attemptAuthentication(HttpServletRequest request, 
												HttpServletResponse response) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		 try {
			 //Create "user" from the request.
			UsernamePasswordAuthRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthRequest.class);
			
			
			Authentication auth=new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
			
			Authentication authenticate=authenticationManager.authenticate(auth);
			return authenticate;
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	//Method executed when an authentication is succeded.
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		
		//We're creating the token
		String token=Jwts.builder()
							.setSubject(authResult.getName())
							//Specifies the body
							.claim("authorities", authResult.getAuthorities())
							.setIssuedAt(new Date())
							//This expires in 2 weeks
							.setExpiration(java.sql.Date.valueOf(LocalDate.now()./*plusWeeks(2)*/plusDays(jwtConfig.getTokenExpirationAfterDays())))
							//The final sign of the token
							.signWith(secretKey)
							.compact();
		
		//Send token to the header. (For Jwt token we use the next format: "Bearer " + the token)
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
		
	}
	
	

}
