package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.ReviewDTO;

public interface IReviewService {
	ReviewDTO addReview(ReviewDTO reviewDTO);
	List<ReviewDTO> getReviewsByHotel(Long hotelId);
	List<ReviewDTO> getReviewsByUser(Long userId);
	void deleteReview(Long reviewId);
}
