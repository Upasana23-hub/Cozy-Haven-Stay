package com.wipro.cozyhaven.dto;

import java.time.LocalDateTime;

import com.wipro.cozyhaven.entity.HotelOwner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HotelDTO {
	
	private Long hotelId;
	@NotBlank(message="Hotel name is required")
	@Size(min=5, max=200)
	private String name;
	@NotBlank(message="Description is required for customers")
	private String description;
	@NotBlank(message="Location is required")
	private String location;
	@PositiveOrZero(message= "Rating cannot be negetive")
	private Double rating;
	private boolean active;
	@NotNull(message= "Owner cannot be null")
	private HotelOwner owner;
	private LocalDateTime createdDate;
	

}
