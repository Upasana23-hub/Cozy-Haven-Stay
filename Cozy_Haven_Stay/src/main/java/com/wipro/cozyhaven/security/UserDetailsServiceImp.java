package com.wipro.cozyhaven.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	    User user = repo.findByEmail(email)
	            .orElseThrow(() ->
	                new UsernameNotFoundException("User not found with email: " + email)
	            );

	    return new UserDetailsImp(user);
	}

}
