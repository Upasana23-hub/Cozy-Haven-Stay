package com.wipro.cozyhaven.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
	
	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;
	
	// Getters and Setters
}
