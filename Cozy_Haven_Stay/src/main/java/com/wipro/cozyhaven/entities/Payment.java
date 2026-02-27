package com.wipro.cozyhaven.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Payments")
@NoArgsConstructor 
@AllArgsConstructor 
@Getter
@Setter
@ToString
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paymentId", nullable = false)
	private Long paymentId;

	@OneToOne
	@JoinColumn(name = "booking_id", nullable = false)
	private Bookings booking;
	
	@Column(name = "amount", nullable = false)
	private Double amount;
	
	@Column(name = "paymentMode", nullable = false)
	private String paymentMode;
	
	@Column(name = "paymentStatus", nullable = false)
	private String paymentStatus;
	
	@Column(name = "paymentDate", nullable = false)
	private LocalDateTime paymentDate;

	
}
