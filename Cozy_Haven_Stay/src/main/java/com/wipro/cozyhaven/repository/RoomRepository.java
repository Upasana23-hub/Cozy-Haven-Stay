package com.wipro.cozyhaven.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {
	
	
	List<Room> findByHotelAndAvailabilityTrue(Hotel hotel);

}
