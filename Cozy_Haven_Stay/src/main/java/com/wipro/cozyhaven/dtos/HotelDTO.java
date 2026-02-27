package com.wipro.cozyhaven.dtos;

import java.time.LocalDateTime;

import com.wipro.cozyhaven.entities.HotelOwner;

import lombok.Data;

@Data
public class HotelDTO {
	
	private Long hotelId;
	private String name;
	private String description;
	private String location;
	private Double rating;
	private boolean active;
	private HotelOwner owner;
	private LocalDateTime createdDate;
	

}
