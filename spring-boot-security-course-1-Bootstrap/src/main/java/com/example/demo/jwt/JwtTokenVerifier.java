package com.example.demo.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


//2nd Filter.
public class JwtTokenVerifier extends OncePerRequestFilter{

	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;

	//Autowiring...
	@Autowired
	public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	
	@Override
	//Method overrided from "OncePerRequestFilter". This method allow us to
	//invoke once per request.
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Getting the data from "authorization".
		String authorizationHeader=request.getHeader(/*"authorization"*/jwtConfig.getAuthorizationHeader());
		
		//If it's wrong, stop the process.
		if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(/*"Bearer "*/jwtConfig.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		String token=authorizationHeader.replace(/*"Bearer "*/jwtConfig.getTokenPrefix(), "");

		try {

			//"Decode"
			Jws<Claims> claimsJws=Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
			
			Claims body =claimsJws.getBody();
			String username=body.getSubject();
			
			//Getting authorities.
			List<Map<String, String>> authorities=(List<Map<String, String>>) body.get("authorities");
			
			//Mapping authorities to be a Collection<GrantedAuthority> from List<Map<String, String>>.
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities=authorities.stream()
				.map(m ->new SimpleGrantedAuthority(m.get("authority")))
				.collect(Collectors.toSet());			
			
			Authentication authentication=new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (JwtException e) {
			// TODO: handle exception
			throw new IllegalStateException(String.format("Token %s can't be trusted",token));
		}
		
		//It makes sure the req and resp passes down to the next filter.
		filterChain.doFilter(request, response);
	}

}
