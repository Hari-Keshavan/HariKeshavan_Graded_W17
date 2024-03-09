package com.test.gradedW17.Model2;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService{
	@Autowired
	AppUserRepository repo;
	
	public void add(AppUser user) {
		repo.save(user);
	}
	
	public List<AppUser> getAll() {
		return repo.findAll();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<AppUser> appUser = repo.findByName(username);
		
		//we are converting the roles from db to grantedAuth
		//userDetails to store the roles / authorities
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		
		for(String temp : appUser.get().getRoles()) {
			GrantedAuthority ga = new SimpleGrantedAuthority(temp);
			grantedAuthorities.add(ga);
		}
		
		//converting appuser to user from userSecurity
		User user = new User(username, appUser.get().getPassword(), grantedAuthorities);
		return user;
	}

}
