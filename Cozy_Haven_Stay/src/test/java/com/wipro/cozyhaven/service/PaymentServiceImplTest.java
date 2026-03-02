package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    
	@Test
	void testAddPayment() {
		User ownerUser = new User();
        ownerUser.setName("Owner User");
        ownerUser.setEmail("owner@example.com");
        ownerUser.setPassword("password");
        ownerUser = userRepository.save(ownerUser);

        HotelOwner owner = new HotelOwner();
        owner.setUserId(ownerUser);
        owner.setBuisnessName("Owner Business");
        owner = hotelOwnerRepository.save(owner);
        
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setLocation("Test Location");
        hotel.setDescription("Nice hotel for testing");
        hotel.setOwner(owner);
        hotel = hotelRepository.save(hotel);

        Room room = new Room();
        room.setRoomNumber("101A");
        room.setRoomType("Deluxe");
        room.setBedType("Queen");
        room.setRoomSize("350 sqft");
        room.setMaxPeople(2);
        room.setBaseFare(1500.0);
        room.setAcAvailable(true);
        room.setAvailability(true);
        room.setAddedAt(LocalDateTime.now());
        room.setHotel(hotel);
        room = roomRepository.save(room);
        
        User user = new User();
        user.setName("Test User");
        user.setEmail("user@example.com");
        user.setPassword("password");
        user = userRepository.save(user);
        
        Bookings booking = new Bookings();
        booking.setHotel(hotel);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckIn(LocalDate.now().plusDays(1));
        booking.setCheckOut(LocalDate.now().plusDays(2));
        booking.setNoOfRooms(1);
        booking.setAdults(2);
        booking.setChildren(0);
        booking.setTotalAmount(3000.0);
        booking.setBookingStatus("Confirmed");
        booking.setPaymentStatus("Pending");
        booking.setBookedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);
        
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBookingId(booking.getBookingId());
        paymentDTO.setAmount(3000.0);
        paymentDTO.setPaymentMode("Credit Card");
        paymentDTO.setPaymentStatus("Completed");

        PaymentDTO savedPayment = paymentService.addPayment(paymentDTO);
        assertNotNull(savedPayment.getPaymentId(), "Payment ID should not be null");
	}

	@Test
	void testGetPaymentById() {
		User ownerUser = new User();
	    ownerUser.setName("Owner2");
	    ownerUser.setEmail("owner2@example.com");
	    ownerUser.setPassword("password");
	    ownerUser = userRepository.save(ownerUser);

	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business 2");
	    owner = hotelOwnerRepository.save(owner);

	    Hotel hotel = new Hotel();
	    hotel.setName("Hotel 2");
	    hotel.setLocation("Location 2");
	    hotel.setDescription("Description 2");
	    hotel.setOwner(owner);
	    hotel = hotelRepository.save(hotel);

	    Room room = new Room();
	    room.setRoomNumber("102B");
	    room.setRoomType("Standard");
	    room.setBedType("Twin");
	    room.setRoomSize("250 sqft");
	    room.setMaxPeople(2);
	    room.setBaseFare(1200.0);
	    room.setAcAvailable(false);
	    room.setAvailability(true);
	    room.setAddedAt(LocalDateTime.now());
	    room.setHotel(hotel);
	    room = roomRepository.save(room);
	    
	    User user = new User();
	    user.setName("User2");
	    user.setEmail("user2@example.com");
	    user.setPassword("password");
	    user = userRepository.save(user);

	    Bookings booking = new Bookings();
	    booking.setHotel(hotel);
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setCheckIn(LocalDate.now().plusDays(3));
	    booking.setCheckOut(LocalDate.now().plusDays(5));
	    booking.setNoOfRooms(1);
	    booking.setAdults(1);
	    booking.setChildren(1);
	    booking.setTotalAmount(2400.0);
	    booking.setBookingStatus("Confirmed");
	    booking.setPaymentStatus("Pending");
	    booking.setBookedAt(LocalDateTime.now());
	    booking = bookingRepository.save(booking);
	    
	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setBookingId(booking.getBookingId());
	    paymentDTO.setAmount(2400.0);
	    paymentDTO.setPaymentMode("Debit Card");
	    paymentDTO.setPaymentStatus("Completed");
	    PaymentDTO savedPayment = paymentService.addPayment(paymentDTO);
	    
	    PaymentDTO fetchedPayment = paymentService.getPaymentById(savedPayment.getPaymentId());
	    assertEquals(savedPayment.getPaymentId(), fetchedPayment.getPaymentId());
	}

	@Test
	void testGetAllPayments() {
		List<PaymentDTO> payments = paymentService.getAllPayments();
	    assertNotNull(payments, "Payments list should not be null");
	}

	@Test
	void testGetPaymentByBookingId() {
		User ownerUser = new User();
	    ownerUser.setName("Owner3");
	    ownerUser.setEmail("owner3@example.com");
	    ownerUser.setPassword("password");
	    ownerUser = userRepository.save(ownerUser);

	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business 3");
	    owner = hotelOwnerRepository.save(owner);

	    Hotel hotel = new Hotel();
	    hotel.setName("Hotel 3");
	    hotel.setLocation("Location 3");
	    hotel.setDescription("Description 3");
	    hotel.setOwner(owner);
	    hotel = hotelRepository.save(hotel);
	    
	    Room room = new Room();
	    room.setRoomNumber("103C");
	    room.setRoomType("Suite");
	    room.setBedType("King");
	    room.setRoomSize("500 sqft");
	    room.setMaxPeople(3);
	    room.setBaseFare(2500.0);
	    room.setAcAvailable(true);
	    room.setAvailability(true);
	    room.setAddedAt(LocalDateTime.now());
	    room.setHotel(hotel);
	    room = roomRepository.save(room);
	    
	    User user = new User();
	    user.setName("User3");
	    user.setEmail("user3@example.com");
	    user.setPassword("password");
	    user = userRepository.save(user);
	    
	    Bookings booking = new Bookings();
	    booking.setHotel(hotel);
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setCheckIn(LocalDate.now().plusDays(1));
	    booking.setCheckOut(LocalDate.now().plusDays(3));
	    booking.setNoOfRooms(1);
	    booking.setAdults(2);
	    booking.setChildren(0);
	    booking.setTotalAmount(5000.0);
	    booking.setBookingStatus("Confirmed");
	    booking.setPaymentStatus("Pending");
	    booking.setBookedAt(LocalDateTime.now());
	    booking = bookingRepository.save(booking);

	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setBookingId(booking.getBookingId());
	    paymentDTO.setAmount(5000.0);
	    paymentDTO.setPaymentMode("UPI");
	    paymentDTO.setPaymentStatus("Completed");
	    paymentService.addPayment(paymentDTO);

	    PaymentDTO fetched = paymentService.getPaymentByBookingId(booking.getBookingId());
	    assertNotNull(fetched);
	    assertEquals(booking.getBookingId(), fetched.getBookingId());
	}

	@Test
	void testGetPaymentsByUserId() {
		User ownerUser = new User();
	    ownerUser.setName("OwnerUser4");
	    ownerUser.setEmail("owner4@example.com");
	    ownerUser.setPassword("password");
	    ownerUser = userRepository.save(ownerUser);

	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business 4");
	    owner = hotelOwnerRepository.save(owner);

	    Hotel hotel = new Hotel();
	    hotel.setName("Hotel 4");
	    hotel.setLocation("Loc 4");
	    hotel.setDescription("Desc 4");
	    hotel.setOwner(owner);
	    hotel = hotelRepository.save(hotel);
	    
	    Room room = new Room();
	    room.setRoomNumber("104D");
	    room.setRoomType("Deluxe");
	    room.setBedType("Queen");
	    room.setRoomSize("350 sqft");
	    room.setMaxPeople(2);
	    room.setBaseFare(1800.0);
	    room.setAcAvailable(true);
	    room.setAvailability(true);
	    room.setAddedAt(LocalDateTime.now());
	    room.setHotel(hotel);
	    room = roomRepository.save(room);
	    
	    User user = new User();
	    user.setName("User4");
	    user.setEmail("user4@example.com");
	    user.setPassword("password");
	    user = userRepository.save(user);
	    
	    Bookings booking = new Bookings();
	    booking.setHotel(hotel);
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setCheckIn(LocalDate.now().plusDays(1));
	    booking.setCheckOut(LocalDate.now().plusDays(2));
	    booking.setNoOfRooms(1);
	    booking.setAdults(2);
	    booking.setChildren(0);
	    booking.setTotalAmount(1800.0);
	    booking.setBookingStatus("Confirmed");
	    booking.setPaymentStatus("Pending");
	    booking.setBookedAt(LocalDateTime.now());
	    booking = bookingRepository.save(booking);
	    
	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setBookingId(booking.getBookingId());
	    paymentDTO.setAmount(1800.0);
	    paymentDTO.setPaymentMode("Credit Card");
	    paymentDTO.setPaymentStatus("Completed");
	    paymentService.addPayment(paymentDTO);
	    
	    List<PaymentDTO> payments = paymentService.getPaymentsByUserId(user.getUserId());
	    assertNotNull(payments);
	    assertEquals(user.getUserId(), payments.get(0).getUserId());
	}

	@Test
	void testGetPaymentsByOwnerId() {
		User ownerUser = new User();
	    ownerUser.setName("OwnerUser5");
	    ownerUser.setEmail("owner5@example.com");
	    ownerUser.setPassword("password");
	    ownerUser = userRepository.save(ownerUser);

	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business 5");
	    owner = hotelOwnerRepository.save(owner);

	    Hotel hotel = new Hotel();
	    hotel.setName("Hotel 5");
	    hotel.setLocation("Loc 5");
	    hotel.setDescription("Desc 5");
	    hotel.setOwner(owner);
	    hotel = hotelRepository.save(hotel);
	    
	    Room room = new Room();
	    room.setRoomNumber("105E");
	    room.setRoomType("Suite");
	    room.setBedType("King");
	    room.setRoomSize("500 sqft");
	    room.setMaxPeople(3);
	    room.setBaseFare(3000.0);
	    room.setAcAvailable(true);
	    room.setAvailability(true);
	    room.setAddedAt(LocalDateTime.now());
	    room.setHotel(hotel);
	    room = roomRepository.save(room);

	    User user = new User();
	    user.setName("User5");
	    user.setEmail("user5@example.com");
	    user.setPassword("password");
	    user = userRepository.save(user);
	    
	    Bookings booking = new Bookings();
	    booking.setHotel(hotel);
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setCheckIn(LocalDate.now().plusDays(2));
	    booking.setCheckOut(LocalDate.now().plusDays(4));
	    booking.setNoOfRooms(1);
	    booking.setAdults(2);
	    booking.setChildren(1);
	    booking.setTotalAmount(3000.0);
	    booking.setBookingStatus("Confirmed");
	    booking.setPaymentStatus("Pending");
	    booking.setBookedAt(LocalDateTime.now());
	    booking = bookingRepository.save(booking);

	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setBookingId(booking.getBookingId());
	    paymentDTO.setAmount(3000.0);
	    paymentDTO.setPaymentMode("UPI");
	    paymentDTO.setPaymentStatus("Completed");
	    paymentService.addPayment(paymentDTO);

	    List<PaymentDTO> payments = paymentService.getPaymentsByOwnerId(owner.getOwnerId());
	    assertNotNull(payments);
	}

	@Test
	void testUpdatePaymentStatus() {
		User ownerUser = new User();
	    ownerUser.setName("OwnerUser6");
	    ownerUser.setEmail("owner6@example.com");
	    ownerUser.setPassword("password");
	    ownerUser = userRepository.save(ownerUser);

	    HotelOwner owner = new HotelOwner();
	    owner.setUserId(ownerUser);
	    owner.setBuisnessName("Owner Business 6");
	    owner = hotelOwnerRepository.save(owner);

	    Hotel hotel = new Hotel();
	    hotel.setName("Hotel 6");
	    hotel.setLocation("Loc 6");
	    hotel.setDescription("Desc 6");
	    hotel.setOwner(owner);
	    hotel = hotelRepository.save(hotel);
	    
	    Room room = new Room();
	    room.setRoomNumber("106F");
	    room.setRoomType("Standard");
	    room.setBedType("Queen");
	    room.setRoomSize("300 sqft");
	    room.setMaxPeople(2);
	    room.setBaseFare(1500.0);
	    room.setAcAvailable(false);
	    room.setAvailability(true);
	    room.setAddedAt(LocalDateTime.now());
	    room.setHotel(hotel);
	    room = roomRepository.save(room);

	    User user = new User();
	    user.setName("User6");
	    user.setEmail("user6@example.com");
	    user.setPassword("password");
	    user = userRepository.save(user);
	    
	    Bookings booking = new Bookings();
	    booking.setHotel(hotel);
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setCheckIn(LocalDate.now().plusDays(1));
	    booking.setCheckOut(LocalDate.now().plusDays(2));
	    booking.setNoOfRooms(1);
	    booking.setAdults(2);
	    booking.setChildren(0);
	    booking.setTotalAmount(1500.0);
	    booking.setBookingStatus("Confirmed");
	    booking.setPaymentStatus("Pending");
	    booking.setBookedAt(LocalDateTime.now());
	    booking = bookingRepository.save(booking);

	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setBookingId(booking.getBookingId());
	    paymentDTO.setAmount(1500.0);
	    paymentDTO.setPaymentMode("Credit Card");
	    paymentDTO.setPaymentStatus("Completed");
	    PaymentDTO savedPayment = paymentService.addPayment(paymentDTO);

	    
	    PaymentDTO updated = paymentService.updatePaymentStatus(savedPayment.getPaymentId(), "Refunded");
	    assertNotNull(updated);
	}

}
