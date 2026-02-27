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


@Entity
@Table(name= "hotel_owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	@Column(name="active")
	private boolean active = true;
	
	@Column(name="created_Date")
	private LocalDateTime createdDate= LocalDateTime.now();

	public void setOwnerId(Long ownerId) {
	
		this.ownerId = ownerId;
	}

	
	
}