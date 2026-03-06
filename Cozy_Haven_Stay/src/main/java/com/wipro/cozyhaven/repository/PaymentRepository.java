package com.wipro.cozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Payment;

import jakarta.transaction.Transactional;


public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Payment findByBookingBookingId(Long bookingId);
	
	List<Payment> findByBookingUserUserId(Long userId);
	
	@Transactional
	void deleteByBookingBookingId(Long bookingId);
}
