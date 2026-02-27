package com.wipro.cozyhaven.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByHotel_HotelId(Long hotelId);
	List<Review> findByUser_UserId(Long userId);
}
