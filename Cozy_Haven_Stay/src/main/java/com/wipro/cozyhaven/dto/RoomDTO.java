package com.wipro.cozyhaven.dto;

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
public class RoomDTO {
	private int roomId;
	private int roomNumber;
	private String roomType;
	private String bedType;
	private String roomSize;
	private int maxPeople;
	private Double baseFare;
	private Boolean AcAvailable;
	private Boolean availability;
	private LocalDateTime addedAt;

}
