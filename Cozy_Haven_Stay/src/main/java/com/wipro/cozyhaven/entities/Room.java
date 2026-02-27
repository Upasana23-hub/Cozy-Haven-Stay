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
	
	@Column(name="Room_number",length=15,nullable=false,unique=true)
	private String roomNumber;
	
	@Column(name="Room_type",nullable=false)
	private String roomType;

	@Column(name="Bed_type")
	private String bedType;
	
	@Column(name="Room_size")
	private String roomSize;
	
	@Column(name="Max_people")
	private int maxPeople;
	
	@Column(name="Base_fare",nullable=false)
	private Double baseFare;
	
	@Column(name="Ac_available")
	private Boolean acAvailable;
	
	@Column(name="Availability")
	private Boolean availability;
	
	@Column(name="Added_at",nullable=false)
	private LocalDateTime addedAt;
	
	@ManyToOne
	@JoinColumn(name="hotel_id",nullable=false)
	private Hotel hotel;
	
	


}
