package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.dto.RegisterRequestDTO;
import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Role;


@SpringBootTest
@Transactional
class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	// Test Register Success
	@Test
	void testRegisterSuccess() {
		
		RegisterRequestDTO request = RegisterRequestDTO.builder()
				.name("Debdutta")
				.email("deb@gmail.com")
				.password("123456")
				.phone("9876543210")
				.address("Kolkata")
				.build();
		
		UserResponseDTO response = userService.register(request);
			
		assertNotNull(response.getUserId());
		assertEquals("Debdutta",response.getName());
		assertEquals(Role.USER, response.getRole());
	}
	
	// Test Duplicate Email
	@Test
	void testRegisterDuplicateEmail() {
		
		RegisterRequestDTO request = RegisterRequestDTO.builder()
				.name("Deb")
				.email("duplicate@gmail.com")
				.password("123456")
				.phone("9999999999")
				.address("India")
				.build();
		
		userService.register(request);
		
		RuntimeException exception = assertThrows(RuntimeException.class, () ->{
			userService.register(request);
		});
		
		assertEquals("Email already registered", exception.getMessage());
	}
	

	// Test Get Profile
	@Test
	void testGetProfileSuccess() {
		
		RegisterRequestDTO request = RegisterRequestDTO.builder()
				.name("ProfileUser")
				.email("profile@gmail.com")
				.password("123456")
				.phone("1111111111")
				.address("Delhi")
				.build();
		
		UserResponseDTO saved = userService.register(request);
		
		UserResponseDTO profile = userService.getProfile(saved.getUserId());
		
			
		
		assertEquals("ProfileUser", profile.getName());
		assertEquals("profile@gmail.com", profile.getEmail());
		
	}
	
	

}
