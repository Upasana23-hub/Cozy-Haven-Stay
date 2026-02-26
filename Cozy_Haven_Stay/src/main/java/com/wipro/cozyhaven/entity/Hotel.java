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

@Entity
@Table(name="hotels")
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
	
	@ManyToOne
	@JoinColumn(name= "owner_id", nullable=false)
	private HotelOwner owner;
	
	@Column(name="created_Date")
	private LocalDateTime created_Date = LocalDateTime.now();

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public HotelOwner getOwner() {
		return owner;
	}

	public void setOwner(HotelOwner owner) {
		this.owner = owner;
	}

	public LocalDateTime getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(LocalDateTime created_Date) {
		this.created_Date = created_Date;
	}

	
}