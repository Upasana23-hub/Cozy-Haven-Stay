package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
class ReviewServiceImplTest {

	@Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;
    
	@Test
	void testAddReview() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReviewsByHotel() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReviewsByUser() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteReview() {
		fail("Not yet implemented");
	}

}
