package com.wipro.cozyhaven.dto;

import java.time.LocalDateTime;

import com.wipro.cozyhaven.entity.HotelOwner;

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
	private LocalDateTime created_Date;
	

}
