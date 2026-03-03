package com.wipro.cozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.cozyhaven.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	List<Hotel> findByOwner_OwnerId(Long userId);
	List<Hotel> findByActiveTrue();
	List<Hotel> findByLocationIgnoreCaseAndActiveTrue(String location);
	List<Hotel> findByLocationIgnoreCaseAndRatingGreaterThanEqualAndActiveTrue(String location, Double rating);

	@Query("SELECT h FROM Hotel h WHERE LOWER(h.location) = LOWER(:location) AND h.rating >= :minRating AND h.active = true")
    List<Hotel> searchByLocationAndMinRating(@Param("location") String location, @Param("minRating") Double minRating);
}
