package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
@Transactional
class HotelServiceImplTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelOwnerService ownerService;

    @Autowired
    private UserRepository userRepository;

  
    @Test
    void testAddHotel() {
        HotelOwner owner = createOwner();
        Hotel hotel = new Hotel();
        hotel.setName("Cozy Haven");
        hotel.setLocation("Kolkata");
        hotel.setDescription("Luxury and affordable stay");
        hotel.setRating(4.8);

        Hotel savedHotel = hotelService.addHotel(owner.getOwnerId(), hotel);
        assertNotNull(savedHotel.getHotelId());
        assertEquals("Cozy Haven", savedHotel.getName());
    }

    
    @Test
    void testGetHotelByOwner() {
        HotelOwner owner = createOwner();
        Hotel hotel = new Hotel();
        hotel.setName("Owner Hotel");
        hotel.setLocation("Mumbai");
        hotel.setDescription("Luxury hotel in Mumbai");
        hotel.setRating(4.5);
        hotelService.addHotel(owner.getOwnerId(), hotel);

        List<Hotel> hotels = hotelService.getHotelByOwner(owner.getOwnerId());

        assertNotNull(hotels);
        assertEquals(1, hotels.size());
        assertEquals("Owner Hotel", hotels.get(0).getName());
    }

    
    @Test
    void testUpdateHotel() {
        HotelOwner owner = createOwner();
        Hotel hotel = new Hotel();
        hotel.setName("Taj Hotel");
        hotel.setLocation("Agra");
        hotel.setDescription("Luxury hotel");

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
        hotel.setDescription("Test Hotel");

        Hotel saved = hotelService.addHotel(owner.getOwnerId(), hotel);
        hotelService.deleteHotel(owner.getOwnerId(), saved.getHotelId());

        List<Hotel> hotels = hotelService.getHotelByOwner(owner.getOwnerId());
        assertTrue(hotels.isEmpty());
    }

    
    @Test
    void testGetAllActiveHotels() {
        List<Hotel> hotels = hotelService.getAllActiveHotels();
        assertNotNull(hotels);
    }

    
    @Test
    void testSearchHotelsByLocation() {
        List<Hotel> hotels = hotelService.searchHotelsByLocation("Pune");
        assertNotNull(hotels);
    }

   
    @Test
    void testSearchHotelsByLocationAndRating() {
        List<Hotel> hotels = hotelService.searchHotelsByLocationAndRating("Pune", 4.0);
        assertNotNull(hotels);
    }

   
    private HotelOwner createOwner() {
        
        User user = new User();
        user.setName("Test User");
        user.setEmail("test" + System.currentTimeMillis() + "@example.com"); 
        user.setPassword("TestPass");
        user = userRepository.save(user);

        HotelOwner owner = new HotelOwner();
        owner.setUser(user); 
        owner.setBuisnessName("JW Marriot");
        owner.setGstNumber("GST" + System.currentTimeMillis());
        owner.setAddress("Kolkata");

        return ownerService.createOwner(owner);
    }
}