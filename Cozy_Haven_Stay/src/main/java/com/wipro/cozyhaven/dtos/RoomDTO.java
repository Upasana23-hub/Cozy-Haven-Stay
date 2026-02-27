package com.wipro.cozyhaven.dtos;

import java.time.LocalDateTime;

import com.wipro.cozyhaven.entities.Hotel;

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
public class RoomDTO {
	private Long roomId;
	private String roomNumber;
	private String roomType;
	private String bedType;
	private String roomSize;
	private int maxPeople;
	private Double baseFare;
	private Boolean acAvailable;
	private Boolean availability;
	private LocalDateTime addedAt;
	private  Long hotelId;

}
