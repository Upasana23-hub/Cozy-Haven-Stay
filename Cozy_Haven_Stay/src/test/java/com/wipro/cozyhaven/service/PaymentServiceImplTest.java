package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.dto.PaymentDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.Room;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.BookingsRepository;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.PaymentRepository;
import com.wipro.cozyhaven.repository.RoomRepository;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
@Transactional
class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingsRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    
    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword("password");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private HotelOwner createOwner(User user) {
        HotelOwner owner = new HotelOwner();
        owner.setUser(user);
        owner.setBuisnessName(user.getName() + " Business");
        owner.setActive(true);
        owner.setApproved(true);
        owner.setCreatedDate(LocalDateTime.now());
        return hotelOwnerRepository.save(owner);
    }

    private Hotel createHotel(HotelOwner owner) {
        Hotel hotel = new Hotel();
        hotel.setName(owner.getBuisnessName() + " Hotel");
        hotel.setLocation("Test Location");
        hotel.setDescription("Test hotel description");
        hotel.setOwner(owner);
        hotel.setActive(true);
        hotel.setCreatedDate(LocalDateTime.now());
        return hotelRepository.save(hotel);
    }

    private Room createRoom(Hotel hotel, String roomNumber) {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType("Deluxe");
        room.setBedType("Queen");
        room.setBaseFare(2000.0);
        room.setAvailability(true);
        room.setAddedAt(LocalDateTime.now());
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    private Bookings createBooking(User user, Hotel hotel, Room room) {
        Bookings booking = new Bookings();
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setCheckIn(LocalDate.now().plusDays(1));
        booking.setCheckOut(LocalDate.now().plusDays(2));
        booking.setNoOfRooms(1);
        booking.setAdults(2);
        booking.setChildren(0);
        booking.setTotalAmount(room.getBaseFare());
        booking.setBookingStatus("Confirmed");
        booking.setPaymentStatus("Pending");
        booking.setBookedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

   

    @Test
    void testAddPayment() {
        User ownerUser = createUser("Owner1", "owner1@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "101A");
        User user = createUser("User1", "user1@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("Credit Card");
        dto.setPaymentStatus("Completed");

        PaymentDTO saved = paymentService.addPayment(dto);
        assertNotNull(saved.getPaymentId());
        assertEquals("Completed", saved.getPaymentStatus());
        assertEquals(booking.getBookingId(), saved.getBookingId());
    }

    @Test
    void testGetPaymentById() {
        User ownerUser = createUser("Owner2", "owner2@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "102B");
        User user = createUser("User2", "user2@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("Debit Card");
        dto.setPaymentStatus("Completed");
        PaymentDTO saved = paymentService.addPayment(dto);

        PaymentDTO fetched = paymentService.getPaymentById(saved.getPaymentId());
        assertNotNull(fetched);
        assertEquals(saved.getPaymentId(), fetched.getPaymentId());
    }

    @Test
    void testGetAllPayments() {
        List<PaymentDTO> paymentsBefore = paymentService.getAllPayments();
        assertNotNull(paymentsBefore);

        User ownerUser = createUser("Owner3", "owner3@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "103C");
        User user = createUser("User3", "user3@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("UPI");
        dto.setPaymentStatus("Completed");
        paymentService.addPayment(dto);

        List<PaymentDTO> paymentsAfter = paymentService.getAllPayments();
        assertNotNull(paymentsAfter);
        assertEquals(paymentsBefore.size() + 1, paymentsAfter.size());
    }

    @Test
    void testGetPaymentByBookingId() {
        User ownerUser = createUser("Owner4", "owner4@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "104D");
        User user = createUser("User4", "user4@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("Cash");
        dto.setPaymentStatus("Completed");
        paymentService.addPayment(dto);

        PaymentDTO fetched = paymentService.getPaymentByBookingId(booking.getBookingId());
        assertNotNull(fetched);
        assertEquals(booking.getBookingId(), fetched.getBookingId());
    }

    @Test
    void testGetPaymentsByUserId() {
        User ownerUser = createUser("Owner5", "owner5@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "105E");
        User user = createUser("User5", "user5@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("Credit Card");
        dto.setPaymentStatus("Completed");
        paymentService.addPayment(dto);

        List<PaymentDTO> payments = paymentService.getPaymentsByUserId(user.getUserId());
        assertNotNull(payments);
        assertEquals(user.getUserId(), payments.get(0).getUserId());
    }

    @Test
    void testGetPaymentsByOwnerId() {
        User ownerUser = createUser("Owner6", "owner6@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "106F");
        User user = createUser("User6", "user6@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("UPI");
        dto.setPaymentStatus("Completed");
        paymentService.addPayment(dto);

        List<PaymentDTO> payments = paymentService.getPaymentsByOwnerId(owner.getOwnerId());
        assertNotNull(payments);
    }

    @Test
    void testUpdatePaymentStatus() {
        User ownerUser = createUser("Owner7", "owner7@test.com");
        HotelOwner owner = createOwner(ownerUser);
        Hotel hotel = createHotel(owner);
        Room room = createRoom(hotel, "107G");
        User user = createUser("User7", "user7@test.com");
        Bookings booking = createBooking(user, hotel, room);

        PaymentDTO dto = new PaymentDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setAmount(booking.getTotalAmount());
        dto.setPaymentMode("Credit Card");
        dto.setPaymentStatus("Completed");
        PaymentDTO saved = paymentService.addPayment(dto);

        PaymentDTO updated = paymentService.updatePaymentStatus(saved.getPaymentId(), "Refunded");
        assertNotNull(updated);
        assertEquals("Refunded", updated.getPaymentStatus());
    }
}