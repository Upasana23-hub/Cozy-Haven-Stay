package com.wipro.cozyhaven.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entities.Hotel;
import com.wipro.cozyhaven.entities.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {
	
	
	List<Room> findByHotelAndAvailabilityTrue(Hotel hotel);

}
