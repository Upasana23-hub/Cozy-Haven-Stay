package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.ReviewDTO;
import com.wipro.cozyhaven.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

	@Autowired
    private ReviewService reviewService;
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.addReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_OWNER','ROLE_ADMIN')")
	@GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByHotel(@PathVariable Long hotelId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByHotel(hotelId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUser(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
}
