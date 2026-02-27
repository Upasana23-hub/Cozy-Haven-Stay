package com.wipro.cozyhaven.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name="hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hotel_id")
	private Long hotelId;
	
	@Column(name="name", nullable=false, length=200)
	private String name;
	
	@Column(name="location", nullable=false, length=100)
	private String location;
	
	@Column(name="description", nullable=false, columnDefinition="TEXT")
	private String description;
	
	@Column(name="rating")
	private Double rating=0.0;
	
	@Column(name="active")
	private boolean active = true;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "owner_id", nullable=false)
	private HotelOwner owner;
	
	@Column(name="created_Date")
	private LocalDateTime createdDate = LocalDateTime.now();

	
	
}