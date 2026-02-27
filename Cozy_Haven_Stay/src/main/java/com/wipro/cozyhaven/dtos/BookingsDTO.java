package com.wipro.cozyhaven.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	private int noOfRooms;
	private int adults;
	private int children;
	
	private Double totalAmount;
	private String bookingStatus; 
	private String paymentStatus;    
	private LocalDateTime bookedAt;
	
	private Long userId;
	private Long roomId;
	

}
