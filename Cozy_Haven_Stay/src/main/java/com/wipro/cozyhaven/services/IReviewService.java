package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.ReviewDTO;

public interface ReviewService {
	ReviewDTO addReview(ReviewDTO reviewDTO);
	List<ReviewDTO> getReviewsByHotel(Long hotelId);
	List<ReviewDTO> getReviewsByUser(Long userId);
	void deleteReview(Long reviewId);
}
