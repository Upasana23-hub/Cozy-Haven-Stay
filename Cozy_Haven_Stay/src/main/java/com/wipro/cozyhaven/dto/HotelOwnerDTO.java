package com.wipro.cozyhaven.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelOwnerDTO {
	
	private Long ownerId;
	@NotNull(message= "userId cannot be null")
	private Long userId;
	@NotBlank(message= "Buisness name is required")
	private String buisnessName;
	@NotBlank(message= "GST number is required")
	private String gstNumber;
	@NotBlank(message= "Address is required")
	private String address;
	private boolean approved;
	private boolean active;
	private LocalDateTime createdDate;

}
