package com.wipro.cozyhaven.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dtos.ReviewDTO;
import com.wipro.cozyhaven.entities.Hotel;
import com.wipro.cozyhaven.entities.Review;
import com.wipro.cozyhaven.entities.User;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repositories.HotelRepository;
import com.wipro.cozyhaven.repositories.ReviewRepository;
import com.wipro.cozyhaven.repositories.UserRepository;


@Service
public class ReviewServiceImpl implements IReviewService {
	
	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    
    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {

    	User user = userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Hotel hotel = hotelRepository.findById(reviewDTO.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        Review review = new Review();
        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        Review savedReview = reviewRepository.save(review);
        reviewDTO.setReviewId(savedReview.getReviewId());
        reviewDTO.setCreatedAt(savedReview.getCreatedAt());
        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> getReviewsByHotel(Long hotelId) {

        List<Review> reviews = reviewRepository.findByHotel_HotelId(hotelId);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewId(review.getReviewId());
            dto.setUserId(review.getUser().getUserId());
            dto.setHotelId(review.getHotel().getHotelId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setCreatedAt(review.getCreatedAt());

            reviewDTOs.add(dto);
        }

        return reviewDTOs;
    }
    

    @Override
    public List<ReviewDTO> getReviewsByUser(Long userId) {

        List<Review> reviews = reviewRepository.findByUser_UserId(userId);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewId(review.getReviewId());
            dto.setUserId(review.getUser().getUserId());
            dto.setHotelId(review.getHotel().getHotelId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setCreatedAt(review.getCreatedAt());

            reviewDTOs.add(dto);
        }
        return reviewDTOs;
    }

	@Override
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
	    reviewRepository.delete(review);
	}

}
