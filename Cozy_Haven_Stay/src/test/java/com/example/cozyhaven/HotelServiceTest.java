package com.example.cozyhaven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.service.HotelService;
@SpringBootTest
class HotelServiceTest {
	
	@Autowired
	private HotelService hotelService;

	@Test
	void testCreatehotel() {
		Hotel hotel = new Hotel();
		hotel.setName("Cozy Haven");
		hotel.setLocation("Kolkata");
		hotel.setDescription("It is a luxury and affordable stay");
		hotel.setRating(4.8);
		
		Hotel savedHotel = hotelService.addHotel(1L, hotel);
		assertNotNull(savedHotel);
		assertEquals("Cozy Haven", savedHotel.getName());
		
	}

}
