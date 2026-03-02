package com.example.cozyhaven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.wipro.cozyhaven.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.service.HotelOwnerService;
import com.wipro.cozyhaven.service.HotelService;
import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class HotelServiceTest {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelOwnerService ownerService;

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
	
	@Test
	void testGetHotelByOwner() {
		HotelOwner owner = createOwner();
		List<Hotel> hotels= hotelService.getHotelByOwner(owner.getOwnerId());
		assertNotNull(hotels);
	}
	
	@Test
	void testUpdateHotel() {
		HotelOwner owner = createOwner();
		Hotel hotel= new Hotel();
		hotel.setName("Taj Hotel");
		hotel.setLocation("Agra");
		hotel.setDescription("It is a Luxurious hotel");
		
		Hotel saved = hotelService.addHotel(owner.getOwnerId(), hotel);
		saved.setName("LivenGarden");
		
		Hotel updated = hotelService.updateHotel(owner.getOwnerId(), saved.getHotelId(), saved);
		assertEquals("LivenGarden", updated.getName());
		
	}
	
	@Test
	void testDeleteHotel() {
		 HotelOwner owner = createOwner();
		 Hotel hotel = new Hotel();
	     hotel.setName("Delete Hotel");
	     hotel.setLocation("Kolkata");
	     hotel.setDescription("Desc");

	        Hotel saved = hotelService.addHotel(owner.getOwnerId(), hotel);

	        hotelService.deleteHotel(owner.getOwnerId(), saved.getHotelId());

	        assertThrows(RuntimeException.class,
	                () -> hotelService.getHotelById(saved.getHotelId()));
	}
	
	 @Test
	    void testGetAllActiveHotels() {
	        List<Hotel> hotels = hotelService.getAllActiveHotels();
	        assertNotNull(hotels);
	 }
	 
	 @Test
	    void testSearchHotelsByLocation() {
	        List<Hotel> hotels =
	                hotelService.searchHotelsByLocation("Pune");
	        assertNotNull(hotels);
	 }
	 
	 @Test
	    void testSearchHotelsByLocationAndRating() {
	        List<Hotel> hotels =
	                hotelService.seachHotelsByLocationAndRating("Pune", 4.0);
	        assertNotNull(hotels);
	}
	 
	 private HotelOwner createOwner() {
	        User user = new User();
	        user.setUserId(2001L);

	        HotelOwner owner = new HotelOwner();
	        owner.setUserId(user);
	        owner.setBuisnessName("Hotel Owner");
	        owner.setGstNumber("GST999");
	        owner.setAddress("Pune");

	        return ownerService.createOwner(owner);
	 }

}
