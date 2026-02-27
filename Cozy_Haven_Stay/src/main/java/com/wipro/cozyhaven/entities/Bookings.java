package com.wipro.cozyhaven.entities;

import java.time.LocalDate;
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
@Table(name="Bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bookings {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Booking_id",nullable=false)
	private Long bookingId;
	
	@Column(name="Check_in",nullable=false)
	private LocalDate checkIn;
	
	@Column(name="Check_out",nullable=false)
	private LocalDate checkOut;
	
	@Column(name="No_of_rooms")
	private int noOfRooms;
	
	@Column(name="Adults",nullable=false)
	private int adults;
	
	@Column(name="Children",nullable=false)
	private int children;
	
	@Column(name="Total_amount",nullable=false)
	private Double totalAmount;
	
	@Column(name="Booking_status",length=20,nullable=false)
	private String bookingStatus;    
	
	@Column(name="Payment_status",length=20,nullable=false)
	private String paymentStatus;    

	
	@Column(name="Booked_at",nullable=false)
	private LocalDateTime bookedAt;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="hotel_id",nullable=false)
	private Hotel hotel;
	
	@ManyToOne
	@JoinColumn(name="room_id",nullable=false)
	private Room room;
	
	
	

}
