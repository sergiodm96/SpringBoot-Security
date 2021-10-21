package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.example.demo.security.ApplicationUserRole.*;
import com.example.demo.student.Student;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		// TODO Auto-generated method stub
		return getApplicationUsers()
				.stream()
				.filter(ApplicationUser -> username.equals(ApplicationUser.getUsername()))
				.findFirst();
	}

	private List<ApplicationUser> getApplicationUsers(){
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser("annasmith", 
						passwordEncoder.encode("password"), 
						STUDENT.GetGrantedAuthorities(), 
						true,
						true,
						true, 
						true),
				
				new ApplicationUser("linda", 
						passwordEncoder.encode("password"), 
						ADMIN.GetGrantedAuthorities(), 
						true,
						true,
						true, 
						true),
				
				new ApplicationUser("tom", 
						passwordEncoder.encode("password"), 
						ADMINTRAINEE.GetGrantedAuthorities(), 
						true,
						true,
						true, 
						true)
				
				);
				
				
				
	
		return applicationUsers;
	}

}
