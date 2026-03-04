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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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

    
    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword("12345");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Utility: Create Owner
    private HotelOwner createOwner() {
        User ownerUser = createUser("Owner User", "owner@example.com");

        HotelOwner owner = new HotelOwner();
        owner.setUser(ownerUser);
        owner.setBuisnessName("Test Business");
        owner.setActive(true);
        owner.setApproved(true);
        owner.setCreatedDate(LocalDateTime.now());
        return hotelOwnerRepository.save(owner);
    }

    // Utility: Create Hotel
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

    private Room createRoom(Hotel hotel, String roomNumber) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType("DELUXE");
        room.setBedType("KING");
        room.setBaseFare(3000.0);
        room.setAvailability(true);
        room.setAddedAt(LocalDateTime.now());
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    // ---- Positive Tests ----

    @Test
    void testCreateBooking() {
        User user = createUser("Test User", "user1@test.com");
        Hotel hotel = createHotel();
        Room room = createRoom(hotel, "101");

        BookingsDTO dto = new BookingsDTO();
        dto.setUserId(user.getUserId());
        dto.setRoomId(room.getRoomId());
        dto.setCheckIn(LocalDate.now().plusDays(1));
        dto.setCheckOut(LocalDate.now().plusDays(2));
        dto.setBookingStatus("CONFIRMED"); 
        dto.setPaymentStatus("PAID");      
        dto.setTotalAmount(3000.0);

        BookingsDTO saved = bookingService.createBooking(dto);

        assertNotNull(saved.getBookingId());
        assertEquals("CONFIRMED", saved.getBookingStatus());
        assertEquals("PAID", saved.getPaymentStatus());
        assertEquals(user.getUserId(), saved.getUserId());
        assertEquals(room.getRoomId(), saved.getRoomId());
    }

    @Test
    void testGetBookingById() {
        User user = createUser("Test User2", "user2@test.com");
        Hotel hotel = createHotel();
        Room room = createRoom(hotel, "105");

        BookingsDTO dto = new BookingsDTO();
        dto.setUserId(user.getUserId());
        dto.setRoomId(room.getRoomId());
        dto.setCheckIn(LocalDate.now().plusDays(3));
        dto.setCheckOut(LocalDate.now().plusDays(5));
        dto.setBookingStatus("CONFIRMED");
        dto.setPaymentStatus("PAID");
        dto.setTotalAmount(5000.0);

        BookingsDTO saved = bookingService.createBooking(dto);
        BookingsDTO found = bookingService.getBookingById(saved.getBookingId());

        assertEquals(saved.getBookingId(), found.getBookingId());
        assertEquals("CONFIRMED", found.getBookingStatus());
        assertEquals("PAID", found.getPaymentStatus());
    }

    @Test
    void testCancelBooking() {
        User user = createUser("Test User3", "user3@test.com");
        Hotel hotel = createHotel();
        Room room = createRoom(hotel, "103");

        BookingsDTO dto = new BookingsDTO();
        dto.setUserId(user.getUserId());
        dto.setRoomId(room.getRoomId());
        dto.setCheckIn(LocalDate.now().plusDays(1));
        dto.setCheckOut(LocalDate.now().plusDays(2));
        dto.setBookingStatus("CONFIRMED");
        dto.setPaymentStatus("PAID");
        dto.setTotalAmount(4000.0);

        BookingsDTO saved = bookingService.createBooking(dto);
        BookingsDTO canceled = bookingService.cancelBooking(saved.getBookingId());

        assertEquals("REFUNDED", canceled.getPaymentStatus());
        assertEquals("CANCELLED", canceled.getBookingStatus());
    }

    @Test
    void testGetBookingByPaymentStatus() {
        User user = createUser("Test User4", "user4@test.com");
        Hotel hotel = createHotel();
        Room room = createRoom(hotel, "104");

        BookingsDTO dto = new BookingsDTO();
        dto.setUserId(user.getUserId());
        dto.setRoomId(room.getRoomId());
        dto.setCheckIn(LocalDate.now().plusDays(1));
        dto.setCheckOut(LocalDate.now().plusDays(2));
        dto.setBookingStatus("CONFIRMED");
        dto.setPaymentStatus("PAID");
        dto.setTotalAmount(4000.0);

        bookingService.createBooking(dto);

        List<BookingsDTO> paidBookings = bookingService.getBookingByPaymentStatus("PAID");
        assertTrue(paidBookings.size() > 0);
        assertEquals("PAID", paidBookings.get(0).getPaymentStatus());
    }
}