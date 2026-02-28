package com.wipro.cozyhaven.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="room_id",nullable=false)
	private Long roomId;
	
	@Column(name="room_number",length=15,nullable=false,unique=true)
	private String roomNumber;
	
	@Column(name="room_type",nullable=false)
	private String roomType;

	@Column(name="bed_type")
	private String bedType;
	
	@Column(name="room_size")
	private String roomSize;
	
	@Column(name="max_people")
	private int maxPeople;
	
	@Column(name="base_fare",nullable=false)
	private Double baseFare;
	
	@Column(name="ac_available")
	private Boolean acAvailable;
	
	@Column(name="availability")
	private Boolean availability;
	
	@Column(name="added_at",nullable=false)
	private LocalDateTime addedAt;
	
	@ManyToOne
	@JoinColumn(name="hotel_id",nullable=false)
	private Hotel hotel;
	
	


}
