package com.wipro.cozyhaven.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingsDTO {
	@NotNull(message = "BookingId is required")
	private Long bookingId;
	
	@NotNull(message = "Check-in date is required")
	private LocalDate checkIn;
	
	@NotNull(message = "Check-out date is required")
	private LocalDate checkOut;
	
	@Min(value = 1, message = "At least one room must be booked")
	private int noOfRooms;
	
	@Min(value = 1, message = "At least one adult is required")
	private int adults;
	
	@Min(value = 0, message = "Children cannot be negative")
	private int children;

    @NotNull(message = "Total amount must be calculated")
    @Positive(message = "Total amount must be greater than 0")
	private Double totalAmount;
    
    @NotBlank(message = "Booking status is required")
	private String bookingStatus; 

    @NotBlank(message = "Payment status is required")
	private String paymentStatus;    
	private LocalDateTime bookedAt;
	
	@NotNull(message = "User ID is required")
	private Long userId;
	
	@NotNull(message = "Room ID is required")
	private Long roomId;
	

}
