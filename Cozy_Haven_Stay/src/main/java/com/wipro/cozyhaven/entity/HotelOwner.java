package com.wipro.cozyhaven.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name= "hotel_owners")
public class HotelOwner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "owner_id")
	private Long ownerId;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable= false, unique=true)
	private User userId;
	
	@Column(name="buisness_name", nullable=false, length=200)
	private String buisnessName;
	
	@Column(name="gst_number", length= 20)
	private String gstNumber;
	
	@Column(name="address",length=255)
	private String address;
	
	@Column(name="approved")
	private boolean approved = false;
	
	@Column(name="created_Date")
	private LocalDateTime created_Date= LocalDateTime.now();

	
}