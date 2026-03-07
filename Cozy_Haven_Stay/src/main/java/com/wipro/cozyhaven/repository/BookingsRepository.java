package com.wipro.cozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.cozyhaven.dto.BookingPaymentDTO;
import com.wipro.cozyhaven.entity.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
	
	List<Bookings> findByPaymentStatus(String paymentStatus);
	
	List<Bookings> findByUserUserId(Long userId);

	@Query("SELECT b FROM Bookings b WHERE b.hotel.hotelId = :hotelId")
	List<Bookings> getBookingsByHotelId(@Param("hotelId") Long hotelId);
	

}
