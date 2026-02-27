package com.wipro.cozyhaven.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.cozyhaven.entities.Role;
import com.wipro.cozyhaven.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	List<User> findByRole(Role role);
}
