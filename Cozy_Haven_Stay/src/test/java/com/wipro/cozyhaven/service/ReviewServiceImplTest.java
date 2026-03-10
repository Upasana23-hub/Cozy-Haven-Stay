package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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

    
    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword("password");
        return userRepository.save(user);
    }

    
    private HotelOwner createOwner(User user, String businessName) {
        HotelOwner owner = new HotelOwner();
        owner.setUser(user);
        owner.setBuisnessName(businessName);
        return hotelOwnerRepository.save(owner);
    }

    
    private Hotel createHotel(HotelOwner owner, String hotelName) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelName);
        hotel.setLocation("Test Location");
        hotel.setDescription("Test Description");
        hotel.setOwner(owner);
        return hotelRepository.save(hotel);
    }

    @Test
    void testAddReview() {
        User ownerUser = createUser("OwnerUser", "owner@example.com");
        HotelOwner owner = createOwner(ownerUser, "Owner Business");
        User reviewUser = createUser("ReviewUser", "reviewuser@example.com");
        Hotel hotel = createHotel(owner, "Test Hotel");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(hotel.getHotelId());
        reviewDTO.setRating(5);
        reviewDTO.setComment("Excellent stay!");

        ReviewDTO savedReview = reviewService.addReview(reviewDTO);
        assertNotNull(savedReview.getReviewId(), "Review ID should not be null");
        assertNotNull(savedReview.getCreatedAt(), "CreatedAt should not be null");
    }

    @Test
    void testGetReviewsByHotel() {
        User ownerUser = createUser("Owner1", "owner1@example.com");
        HotelOwner owner = createOwner(ownerUser, "Owner Business1");
        User reviewUser = createUser("ReviewUser1", "reviewuser1@example.com");
        Hotel hotel = createHotel(owner, "Test Hotel1");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(hotel.getHotelId());
        reviewDTO.setRating(4);
        reviewDTO.setComment("Very good stay!");
        reviewService.addReview(reviewDTO);

        List<ReviewDTO> reviews = reviewService.getReviewsByHotel(hotel.getHotelId());
        assertNotNull(reviews, "Review list should not be null");
        assertTrue(reviews.size() > 0);
    }

    @Test
    void testGetReviewsByUser() {
        User ownerUser = createUser("Owner2", "owner2@example.com");
        HotelOwner owner = createOwner(ownerUser, "Owner Business2");
        User reviewUser = createUser("ReviewUser2", "reviewuser2@example.com");
        Hotel hotel = createHotel(owner, "Test Hotel2");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(hotel.getHotelId());
        reviewDTO.setRating(3);
        reviewDTO.setComment("It was okay");
        reviewService.addReview(reviewDTO);

        List<ReviewDTO> reviews = reviewService.getReviewsByUser(reviewUser.getUserId());
        assertNotNull(reviews, "Review list should not be null");
        assertTrue(reviews.size() > 0);
    }

    @Test
    void testDeleteReview() {
        User ownerUser = createUser("Owner3", "owner3@example.com");
        HotelOwner owner = createOwner(ownerUser, "Owner Business3");
        User reviewUser = createUser("ReviewUser3", "reviewuser3@example.com");
        Hotel hotel = createHotel(owner, "Test Hotel3");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(hotel.getHotelId());
        reviewDTO.setRating(2);
        reviewDTO.setComment("Not good");

        ReviewDTO savedReview = reviewService.addReview(reviewDTO);
        Long reviewId = savedReview.getReviewId();
        reviewService.deleteReview(reviewId);
        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(reviewId));
    }
    
    
    @Test
    void testUpdateReview() {
      
        User ownerUser = createUser("Owner4", "owner4@example.com");
        HotelOwner owner = createOwner(ownerUser, "Owner Business4");
        User reviewUser = createUser("ReviewUser4", "reviewuser4@example.com");
        Hotel hotel = createHotel(owner, "Test Hotel4");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(reviewUser.getUserId());
        reviewDTO.setHotelId(hotel.getHotelId());
        reviewDTO.setRating(3);
        reviewDTO.setComment("It was decent");
        ReviewDTO savedReview = reviewService.addReview(reviewDTO);

        ReviewDTO updatedReview = reviewService.updateReview(
                savedReview.getReviewId(),
                reviewUser.getUserId(),
                5,
                "Actually it was amazing!"
        );
        assert(updatedReview.getReviewId().equals(savedReview.getReviewId()));
    }
}