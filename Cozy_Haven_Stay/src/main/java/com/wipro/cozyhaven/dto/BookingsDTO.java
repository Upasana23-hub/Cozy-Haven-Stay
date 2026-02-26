package com.wipro.cozyhaven.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingsDTO {
	private Long bookingId;
	
	private LocalDate chekckIn;
	private LocalDate checkOut;
	
	private int noOfRooms;
	private int adults;
	private int children;
	
	private Double totalAmount;
	private String status;    
	private LocalDate bookedAt;
	

}
