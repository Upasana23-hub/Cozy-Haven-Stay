package com.wipro.cozyhaven.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HotelOwnerDTO {
	
	private Long ownerId;
	private Long userId;
	private String buisnessName;
	private String gstNumber;
	private String address;
	private boolean approved;
	private boolean active;
	private LocalDateTime createdDate;

}
