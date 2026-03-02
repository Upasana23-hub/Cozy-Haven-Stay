package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.entity.Role;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
@Transactional
class AdminServiceImplTest {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	// Test Delete User
	
	@Test
	void testDeleteUserSuccess() {
		
		User user = User.builder()
				.name("TestUser")
				.email("testuser@gmail.com")
				.password("123456")
				.role(Role.USER)
				.build();
		
		user = userRepository.save(user);
		
		adminService.deleteUser(user.getUserId());
		
		assertFalse(userRepository.findById(user.getUserId()).isPresent());
	}
	
	// Test Prevent Delete Admin
	
	@Test
	void testDeleteAdminShouldFail() {
		
		 User admin = userRepository.save(
	                User.builder()
	                    .name("AdminUser")
	                    .email("admin@gmail.com")
	                    .password("123456")
	                    .role(Role.ADMIN)
	                    .build()
	        );

		
		RuntimeException exception = assertThrows(RuntimeException.class, () ->{
			adminService.deleteUser(admin.getUserId());
		});
		
		assertEquals("Cannot delete another admin", exception.getMessage());
	}

}
