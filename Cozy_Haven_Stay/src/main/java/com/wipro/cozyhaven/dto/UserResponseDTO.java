package com.wipro.cozyhaven.dto;

import lombok.*;
import com.wipro.cozyhaven.entity.Role;

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
}
