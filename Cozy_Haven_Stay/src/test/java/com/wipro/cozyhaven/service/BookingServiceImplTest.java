package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.dto.BookingsDTO;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.Room;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.RoomRepository;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
class BookingServiceImplTest {

	    @Autowired
	    private BookingService bookingService;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private HotelOwnerRepository hotelOwnerRepository;

	    @Autowired
	    private HotelRepository hotelRepository;

	    @Autowired
	    private RoomRepository roomRepository;

	    private User createUser() {
	        User user = new User();
	        user.setName("Test User");
	        user.setEmail("testuser@example.com");
	        user.setPassword("12345");
	        user.setCreatedAt(LocalDateTime.now());
	        return userRepository.save(user);
	    }

	    private HotelOwner createOwner() {
	        User ownerUser = new User();
	        ownerUser.setName("Owner User");
	        ownerUser.setEmail("owner@example.com");
	        ownerUser.setPassword("owner123");
	        ownerUser.setCreatedAt(LocalDateTime.now());
	        ownerUser = userRepository.save(ownerUser);

	        HotelOwner owner = new HotelOwner();
	        owner.setUserId(ownerUser);
	        owner.setBuisnessName("Test Business");
	        owner.setActive(true);
	        owner.setApproved(true);
	        owner.setCreatedDate(LocalDateTime.now());
	        return hotelOwnerRepository.save(owner);
	    }
	    private Hotel createHotel() {
	        HotelOwner owner = createOwner(); 
	        Hotel hotel = new Hotel();
	        hotel.setName("Test Hotel");
	        hotel.setLocation("Chennai");
	        hotel.setDescription("Test Description");
	        hotel.setOwner(owner);        
	        hotel.setActive(true);
	        hotel.setCreatedDate(LocalDateTime.now());
	        return hotelRepository.save(hotel);
	    }

	    private Room createRoom(Hotel hotel) {
	        Room room = new Room();
	        room.setRoomNumber("101");
	        room.setRoomType("DELUXE");
	        room.setBedType("KING");
	        room.setBaseFare(3000.0);
	        room.setAvailability(true);
	        room.setAddedAt(LocalDateTime.now());
	        room.setHotel(hotel);
	        return roomRepository.save(room);
	    }

	    @Test
	    void testCreateBooking() {
	        User user = createUser();
	        Hotel hotel = createHotel();
	        Room room = createRoom(hotel);

	        BookingsDTO bookingDTO = new BookingsDTO();
	        bookingDTO.setUserId(user.getUserId());
	        bookingDTO.setRoomId(room.getRoomId());
	        bookingDTO.setCheckIn(LocalDate.now().plusDays(1));
	        bookingDTO.setCheckOut(LocalDate.now().plusDays(3));
	        bookingDTO.setPaymentStatus("PAID");
	        bookingDTO.setTotalAmount(6000.0);

	        BookingsDTO saved = bookingService.createBooking(bookingDTO);

	        assertNotNull(saved);
	        assertEquals(user.getUserId(), saved.getUserId());
	        assertEquals(room.getRoomId(), saved.getRoomId());
	        assertEquals("PAID", saved.getPaymentStatus());
	    }

	    @Test
	    void testGetAllBookings() {
	        List<BookingsDTO> allBookings = bookingService.getAllBookings();
	        assertNotNull(allBookings);
	    }

	    @Test
	    void testGetBookingById() {
	        User user = createUser();
	        Hotel hotel = createHotel();
	        Room room = createRoom(hotel);

	        BookingsDTO dto = new BookingsDTO();
	        dto.setUserId(user.getUserId());
	        dto.setRoomId(room.getRoomId());
	        dto.setCheckIn(LocalDate.now().plusDays(2));
	        dto.setCheckOut(LocalDate.now().plusDays(4));
	        dto.setPaymentStatus("PAID");
	        dto.setTotalAmount(5000.0);

	        BookingsDTO saved = bookingService.createBooking(dto);
	        BookingsDTO found = bookingService.getBookingById(saved.getBookingId());

	        assertEquals(saved.getBookingId(), found.getBookingId());
	        assertEquals(saved.getUserId(), found.getUserId());
	    }

	    @Test
	    void testCancelBooking() {
	        User user = createUser();
	        Hotel hotel = createHotel();
	        Room room = createRoom(hotel);

	        BookingsDTO dto = new BookingsDTO();
	        dto.setUserId(user.getUserId());
	        dto.setRoomId(room.getRoomId());
	        dto.setCheckIn(LocalDate.now().plusDays(1));
	        dto.setCheckOut(LocalDate.now().plusDays(2));
	        dto.setPaymentStatus("PAID");
	        dto.setTotalAmount(4000.0);

	        BookingsDTO saved = bookingService.createBooking(dto);
	        BookingsDTO canceled = bookingService.cancelBooking(saved.getBookingId());

	        assertEquals("REFUNDED", canceled.getPaymentStatus());
	    }

	    @Test
	    void testGetBookingByPaymentStatus() {
	        User user = createUser();
	        Hotel hotel = createHotel();
	        Room room = createRoom(hotel);

	        BookingsDTO dto = new BookingsDTO();
	        dto.setUserId(user.getUserId());
	        dto.setRoomId(room.getRoomId());
	        dto.setCheckIn(LocalDate.now().plusDays(1));
	        dto.setCheckOut(LocalDate.now().plusDays(2));
	        dto.setPaymentStatus("PAID");
	        dto.setTotalAmount(4000.0);

	        bookingService.createBooking(dto);

	        List<BookingsDTO> paidBookings = bookingService.getBookingByPaymentStatus("PAID");
	        assertTrue(paidBookings.size() > 0);
	        assertEquals("PAID", paidBookings.get(0).getPaymentStatus());
	    }
	}
	   