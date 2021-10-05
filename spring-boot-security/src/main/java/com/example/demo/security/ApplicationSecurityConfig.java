package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.auth.ApplicationUserService;

import static com.example.demo.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity

//To tell the config we want to use this annotations for permission/role base authentication(@PreAuthorize()) *StudentManagementController.class*
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
//		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//		.and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(STUDENT.name())
		.anyRequest()
		.authenticated()
		.and()
		//.httpBasic();
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/courses", true)
			.passwordParameter("password")
			.usernameParameter("username")
		.and()
		.rememberMe()
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
			.key("somethingverysecured")
			.rememberMeParameter("remember-me")
		.and()
		.logout()
			.logoutUrl("/logout")
			//We can use this line because CSRF token is disable. Otherwise, we should be using POST method.
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID","remember-me")
			.logoutSuccessUrl("/login");
		
	}

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}
	
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		UserDetails annasmithUser = User.builder()
//				.username("annasmith")
//				.password(passwordEncoder.encode("password"))
////				.roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
//				.authorities(STUDENT.GetGrantedAuthorities())
//				.build();
//		
//		UserDetails lindaUser= User.builder()
//			.username("linda")
//			.password(passwordEncoder.encode("password123"))
////			.roles(ApplicationUserRole.ADMIN.name()) //ROLE_ADMIN
//			.authorities(ADMIN.GetGrantedAuthorities())
//			.build();
//		
//		UserDetails tomUser= User.builder()
//				.username("tom")
//				.password(passwordEncoder.encode("password123"))
////				.roles(ApplicationUserRole.ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
//				.authorities(ADMINTRAINEE.GetGrantedAuthorities())
//				.build();
//	
//
//		return new InMemoryUserDetailsManager(annasmithUser, lindaUser, tomUser);
//
//	}
}
