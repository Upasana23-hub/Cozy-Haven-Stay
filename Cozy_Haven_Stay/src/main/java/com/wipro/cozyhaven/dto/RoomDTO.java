package com.wipro.cozyhaven.dto;

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
public class RoomDTO {
	
	@NotBlank(message = "Room type cannot be empty")
	private Long roomId;
	
	@NotBlank(message = "Room number cannot be empty")
	private String roomNumber;
	
	@NotBlank(message = "Room type cannot be empty")
	private String roomType;
	
	@NotBlank(message = "Bed type cannot be empty")
	private String bedType;
	
	@NotBlank(message = "Room size cannot be empty")
	private String roomSize;
	
	@Min(value = 1, message = "Max people must be at least 1")
	private int maxPeople;
	
	@NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be greater than 0")
	private Double baseFare;
	
	 @NotNull(message = "AC availability must be specified")
	private Boolean acAvailable;
	
	@NotNull(message = "Availability status must be specified")
	private Boolean availability;
	
	private LocalDateTime addedAt;
	
	@NotNull(message = "Hotel ID is required")
	private  Long hotelId;

}
