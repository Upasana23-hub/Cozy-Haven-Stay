package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.dto.ReviewDTO;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.ReviewRepository;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
class ReviewServiceImplTest {
    
	@Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private HotelOwnerRepository hotelOwnerRepository;
    
    @Test
    void testAddReview() {
    	User ownerUser = new User();
        ownerUser.setName("Owner User");
        ownerUser.setEmail("owneruser@example.com");
        ownerUser.setPassword("password");
        ownerUser = userRepository.save(ownerUser);
        
        HotelOwner owner = new HotelOwner();
        owner.setUserId(ownerUser);
        owner.setBuisnessName("Owner Business");
        owner = hotelOwnerRepository.save(owner);
        
        
        User reviewUser = new User();
        reviewUser.setName("Test User");
        reviewUser.setEmail("testuser@example.com");
        reviewUser.setPassword("password");
        reviewUser = userRepository.save(reviewUser);
        
        Hotel testHotel = new Hotel();
        testHotel.setName("Test Hotel");
        testHotel.setLocation("Test Location");
        testHotel.setDescription("Nice hotel for testing");
        testHotel.setOwner(owner); 
        testHotel = hotelRepository.save(testHotel);
        
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(testHotel.getHotelId());
        reviewDTO.setRating(5);
        reviewDTO.setComment("Excellent stay!");
        
        ReviewDTO savedReview = reviewService.addReview(reviewDTO);
        
        assertNotNull(savedReview.getReviewId(), "Review ID should not be null");
    }

	@Test
	void testGetReviewsByHotel() {
		User ownerUser = new User();
	    ownerUser.setName("Owner1");
	    ownerUser.setEmail("owneruser1@example.com");
	    ownerUser.setPassword("owner");
	    ownerUser = userRepository.save(ownerUser);
	    
	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser); 
	    owner.setBuisnessName("Owner Business1");
	    owner = hotelOwnerRepository.save(owner);
	    
	    Hotel testHotel = new Hotel();
	    testHotel.setName("Test Hotel1");
	    testHotel.setLocation("Test Location1");
	    testHotel.setDescription("Nice hotel for testing1");
	    testHotel.setOwner(owner);
	    testHotel = hotelRepository.save(testHotel);
	    
	    User reviewUser = new User();
	    reviewUser.setName("Review User");
	    reviewUser.setEmail("reviewuser1@example.com");
	    reviewUser.setPassword("password1");
	    reviewUser = userRepository.save(reviewUser);
	    
	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setUserId(reviewUser.getUserId());
	    reviewDTO.setHotelId(testHotel.getHotelId());
	    reviewDTO.setRating(4);
	    reviewDTO.setComment("Very good stay!");
	    reviewService.addReview(reviewDTO);
	    
	    List<ReviewDTO> reviews = reviewService.getReviewsByHotel(testHotel.getHotelId());
	    
	    assertNotNull(reviews, "Review list should not be null");
	}
	

	@Test
	void testGetReviewsByUser() {
	    
	    User ownerUser = new User();
	    ownerUser.setName("Owner2");
	    ownerUser.setEmail("owner2@example.com");
	    ownerUser.setPassword("owner2");
	    ownerUser = userRepository.save(ownerUser);
	    
	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business2");
	    owner = hotelOwnerRepository.save(owner);
	    
	    Hotel testHotel = new Hotel();
	    testHotel.setName("Test Hotel2");
	    testHotel.setLocation("Test Location2");
	    testHotel.setDescription("Nice hotel for testing2");
	    testHotel.setOwner(owner);
	    testHotel = hotelRepository.save(testHotel);
	    
	    
	    User reviewUser = new User();
	    reviewUser.setName("Review User2");
	    reviewUser.setEmail("reviewuser2@example.com");
	    reviewUser.setPassword("password2");
	    reviewUser = userRepository.save(reviewUser);
	    
	    
	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setUserId(reviewUser.getUserId());
	    reviewDTO.setHotelId(testHotel.getHotelId());
	    reviewDTO.setRating(3);
	    reviewDTO.setComment("It was okay");
	    reviewService.addReview(reviewDTO);
	    
	    
	    List<ReviewDTO> reviews = reviewService.getReviewsByUser(reviewUser.getUserId());
	    
	    assertNotNull(reviews, "Review list should not be null");
	
	}

	@Test
	void testDeleteReview() {
	    
	    User ownerUser = new User();
	    ownerUser.setName("Owner3");
	    ownerUser.setEmail("owner3@example.com");
	    ownerUser.setPassword("owner3");
	    ownerUser = userRepository.save(ownerUser);
	    
	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business3");
	    owner = hotelOwnerRepository.save(owner);
	    
	    Hotel testHotel = new Hotel();
	    testHotel.setName("Test Hotel3");
	    testHotel.setLocation("Test Location3");
	    testHotel.setDescription("Nice hotel for testing3");
	    testHotel.setOwner(owner);
	    testHotel = hotelRepository.save(testHotel);
	    
	    
	    User reviewUser = new User();
	    reviewUser.setName("Review User3");
	    reviewUser.setEmail("reviewuser3@example.com");
	    reviewUser.setPassword("password3");
	    reviewUser = userRepository.save(reviewUser);
	    
	    
	    ReviewDTO reviewDTO = new ReviewDTO();
	    reviewDTO.setUserId(reviewUser.getUserId());
	    reviewDTO.setHotelId(testHotel.getHotelId());
	    reviewDTO.setRating(2);
	    reviewDTO.setComment("Not good");
	    ReviewDTO savedReview = reviewService.addReview(reviewDTO);
	    
	    Long reviewId = savedReview.getReviewId();
	    
	    
	    reviewService.deleteReview(reviewId);
	    
	    
	    assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(reviewId));
	}

}
