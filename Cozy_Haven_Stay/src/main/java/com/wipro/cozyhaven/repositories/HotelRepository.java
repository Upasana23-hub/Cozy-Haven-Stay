package com.wipro.cozyhaven.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	List<Hotel> findByOwner_OwnerId(Long userId);
	List<Hotel> findByActiveTrue();
	List<Hotel> findByLocationIgnoreCaseAndActiveTrue(String location);
	List<Hotel> findByLocationIgnoreCaseAndRatingGreaterThanEqualAndActiveTrue(String location, Double rating);

}
