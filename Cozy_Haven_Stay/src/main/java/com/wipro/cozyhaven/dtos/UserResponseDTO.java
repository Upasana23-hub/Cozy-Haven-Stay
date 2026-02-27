package com.wipro.cozyhaven.dtos;

import com.wipro.cozyhaven.entities.Role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
	
	private Long userId;
	private String name;
	private String email;
	private Role role;
	private String phone;
	private String address;
	
	// Getters and Setters
}
