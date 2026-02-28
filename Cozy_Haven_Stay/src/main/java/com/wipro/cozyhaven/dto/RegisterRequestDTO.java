package com.wipro.cozyhaven.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message ="Password is required")
	@Size(min = 6, message = "Password must be at least 6 charecters")
	private String password;
	
	@NotBlank(message = "Phone is required")
	private String phone;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	// Getters and Setters
}
