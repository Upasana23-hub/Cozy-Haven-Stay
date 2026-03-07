package com.wipro.cozyhaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Review;

import jakarta.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByHotel_HotelId(Long hotelId);
	List<Review> findByUser_UserId(Long userId);
	
	@Transactional
	void deleteByUser_UserId(Long userId);
	
	Optional<Review> findByReviewIdAndUser_UserId(Long reviewId, Long userId);
}
