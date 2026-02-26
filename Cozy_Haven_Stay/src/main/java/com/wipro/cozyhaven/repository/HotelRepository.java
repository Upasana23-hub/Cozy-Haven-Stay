package com.wipro.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.wipro.cozyhaven.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	List<Hotel> findByOwner_OwnerId(Long userId);

}
