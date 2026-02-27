package com.wipro.cozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
	
	List<Bookings> findByPaymentStatus(String paymentStatus);

}
