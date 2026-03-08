package com.wipro.cozyhaven.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dto.BookingPaymentDTO;
import com.wipro.cozyhaven.dto.BookingsDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Payment;
import com.wipro.cozyhaven.entity.Room;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repository.BookingsRepository;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.PaymentRepository;
import com.wipro.cozyhaven.repository.RoomRepository;
import com.wipro.cozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingsRepository bookingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RoomRepository roomRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public BookingsDTO createBooking(BookingsDTO bookingDTO) {
	    User user = userRepository.findById(bookingDTO.getUserId())
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    Room room = roomRepository.findById(bookingDTO.getRoomId())
	            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

	    Bookings booking = new Bookings();
	    booking.setUser(user);
	    booking.setRoom(room);
	    booking.setHotel(room.getHotel());
	    booking.setBookedAt(LocalDateTime.now());
	    booking.setCheckIn(bookingDTO.getCheckIn());
	    booking.setCheckOut(bookingDTO.getCheckOut());
	    booking.setAdults(bookingDTO.getAdults());           
	    booking.setChildren(bookingDTO.getChildren());       
	    booking.setNoOfRooms(bookingDTO.getNoOfRooms());     
	    booking.setBookingStatus(bookingDTO.getBookingStatus() != null ? bookingDTO.getBookingStatus() : "BOOKED");
	    booking.setPaymentStatus(bookingDTO.getPaymentStatus() != null ? bookingDTO.getPaymentStatus() : "PENDING");
	    booking.setTotalAmount(bookingDTO.getTotalAmount());

	    Bookings savedBooking = bookingRepository.save(booking);

	    BookingsDTO savedDTO = new BookingsDTO();
	    savedDTO.setBookingId(savedBooking.getBookingId());
	    savedDTO.setUserId(savedBooking.getUser().getUserId());
	    savedDTO.setRoomId(savedBooking.getRoom().getRoomId());
	    savedDTO.setCheckIn(savedBooking.getCheckIn());
	    savedDTO.setCheckOut(savedBooking.getCheckOut());
	    savedDTO.setAdults(savedBooking.getAdults());        
	    savedDTO.setChildren(savedBooking.getChildren());    
	    savedDTO.setNoOfRooms(savedBooking.getNoOfRooms()); 
	    savedDTO.setPaymentStatus(savedBooking.getPaymentStatus());
	    savedDTO.setTotalAmount(savedBooking.getTotalAmount());
	    savedDTO.setBookingStatus(savedBooking.getBookingStatus());
	    savedDTO.setBookedAt(savedBooking.getBookedAt());

	    return savedDTO;
	}
	
	
	
	@Override
	public List<BookingsDTO> getAllBookings() {

        List<Bookings> bookings = bookingRepository.findAll();
        List<BookingsDTO> bookingDTOs = new ArrayList<>();

        for (Bookings booking : bookings) {

            BookingsDTO dto = new BookingsDTO();
            dto.setBookingId(booking.getBookingId());
            dto.setUserId(booking.getUser().getUserId());
            dto.setRoomId(booking.getRoom().getRoomId());
            dto.setCheckIn(booking.getCheckIn());
            dto.setCheckOut(booking.getCheckOut());
            dto.setPaymentStatus(booking.getPaymentStatus());
            dto.setTotalAmount(booking.getTotalAmount());
            dto.setBookingStatus(booking.getBookingStatus()); 
            dto.setCheckIn(booking.getCheckIn());     
            dto.setCheckOut(booking.getCheckOut());   
            dto.setBookedAt(booking.getBookedAt());

            bookingDTOs.add(dto);
        }
        return bookingDTOs;
	}

	@Override
	public BookingsDTO getBookingById(Long bookingId) {
		Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Booking not found"));

        BookingsDTO dto = new BookingsDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setRoomId(booking.getRoom().getRoomId());
        dto.setCheckIn(booking.getCheckIn());
        dto.setCheckOut(booking.getCheckOut());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setBookingStatus(booking.getBookingStatus()); 
        dto.setAdults(booking.getAdults());   
        dto.setChildren(booking.getChildren()); 
        dto.setNoOfRooms(booking.getNoOfRooms()); 
        return dto;
	}

	@Override
	public BookingsDTO cancelBooking(Long bookingId) {

        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Booking not found"));

        booking.setBookingStatus("CANCELLED");
        booking.setPaymentStatus("REFUNDED");

        Bookings updatedBooking = bookingRepository.save(booking);

        BookingsDTO dto = new BookingsDTO();
        dto.setBookingId(updatedBooking.getBookingId());
        dto.setUserId(updatedBooking.getUser().getUserId());
        dto.setRoomId(updatedBooking.getRoom().getRoomId());
        dto.setCheckIn(updatedBooking.getCheckIn());
        dto.setCheckOut(updatedBooking.getCheckOut());
        dto.setBookedAt(updatedBooking.getBookedAt());       
        dto.setAdults(updatedBooking.getAdults());           
        dto.setChildren(updatedBooking.getChildren());     
        dto.setNoOfRooms(updatedBooking.getNoOfRooms());
        dto.setPaymentStatus(updatedBooking.getPaymentStatus());
        dto.setTotalAmount(updatedBooking.getTotalAmount());
        dto.setBookingStatus(booking.getBookingStatus()); 
	
		return dto;
	}

	@Override
	public List<BookingsDTO> getBookingByPaymentStatus(String paymentStatus) {
		 List<Bookings> bookings = bookingRepository.findByPaymentStatus(paymentStatus);
	        List<BookingsDTO> bookingDTOs = new ArrayList<>();

	        for (Bookings booking : bookings) {

	            BookingsDTO dto = new BookingsDTO();
	            dto.setBookingId(booking.getBookingId());
	            dto.setUserId(booking.getUser().getUserId());
	            dto.setRoomId(booking.getRoom().getRoomId());
	            dto.setCheckIn(booking.getCheckIn());
	            dto.setCheckOut(booking.getCheckOut());
	            dto.setPaymentStatus(booking.getPaymentStatus());
	            dto.setTotalAmount(booking.getTotalAmount());

	            bookingDTOs.add(dto);
	        }

	        return bookingDTOs;
	}
	@Override
	public List<BookingsDTO> getBookingsByUserId(Long userId) {

	    List<Bookings> bookings = bookingRepository.findByUserUserId(userId);
	    List<BookingsDTO> bookingDTOs = new ArrayList<>();

	    for (Bookings booking : bookings) {

	        BookingsDTO dto = new BookingsDTO();
	        dto.setBookingId(booking.getBookingId());
	        dto.setUserId(booking.getUser().getUserId());
	        dto.setRoomId(booking.getRoom().getRoomId());
	        dto.setCheckIn(booking.getCheckIn());
	        dto.setCheckOut(booking.getCheckOut());
	        dto.setBookedAt(booking.getBookedAt());
	        dto.setPaymentStatus(booking.getPaymentStatus());
	        dto.setTotalAmount(booking.getTotalAmount());
	        dto.setBookingStatus(booking.getBookingStatus());
	        dto.setAdults(booking.getAdults());  
	        dto.setChildren(booking.getChildren()); 
	        dto.setNoOfRooms(booking.getNoOfRooms()); 

	        bookingDTOs.add(dto);
	    }

	    return bookingDTOs;
	}
	@Override
	public List<BookingsDTO> getBookingsByHotelId(Long hotelId) {

		List<Bookings> bookings = bookingRepository.getBookingsByHotelId(hotelId);
	    List<BookingsDTO> bookingDTOs = new ArrayList<>();

	    for (Bookings booking : bookings) {

	        if (booking.getHotel().getHotelId().equals(hotelId)) {

	            BookingsDTO dto = new BookingsDTO();
	            dto.setBookingId(booking.getBookingId());
	            dto.setUserId(booking.getUser().getUserId());
	            dto.setRoomId(booking.getRoom().getRoomId());
	            dto.setCheckIn(booking.getCheckIn());       
	            dto.setCheckOut(booking.getCheckOut());    
	            dto.setBookedAt(booking.getBookedAt()); 
	            dto.setCheckIn(booking.getCheckIn());
	            dto.setCheckOut(booking.getCheckOut());
	            dto.setPaymentStatus(booking.getPaymentStatus());
	            dto.setTotalAmount(booking.getTotalAmount());
	            dto.setBookingStatus(booking.getBookingStatus());
	            dto.setAdults(booking.getAdults());
	            dto.setChildren(booking.getChildren());
	            dto.setNoOfRooms(booking.getNoOfRooms());

	            bookingDTOs.add(dto);
	        }
	    }

	    return bookingDTOs;
	}
	
	
	@Override
	@Transactional
	public String createBookingWithPayment(BookingPaymentDTO dto) {

	    Bookings booking = new Bookings();

	    booking.setCheckIn(dto.getCheckIn());
	    booking.setCheckOut(dto.getCheckOut());
	    booking.setAdults(dto.getAdults());
	    booking.setChildren(dto.getChildren());
	    booking.setNoOfRooms(dto.getNoOfRooms());
	    booking.setTotalAmount(dto.getTotalAmount());
	    booking.setBookingStatus("CONFIRMED");
	    booking.setBookedAt(LocalDateTime.now());

	    booking.setUser(userRepository.findById(dto.getUserId()).orElse(null));
	    booking.setHotel(hotelRepository.findById(dto.getHotelId()).orElse(null));
	    booking.setRoom(roomRepository.findById(dto.getRoomId()).orElse(null));

	    String paymentMode = dto.getPaymentMode();

	    if(paymentMode.equalsIgnoreCase("CARD") ||
	       paymentMode.equalsIgnoreCase("UPI") ||
	       paymentMode.equalsIgnoreCase("BANK")) {

	        booking.setPaymentStatus("PAID");

	    } else {
	        booking.setPaymentStatus("PENDING");
	    }

	    Bookings savedBooking = bookingRepository.save(booking);

	    Payment payment = new Payment();

	    payment.setBooking(savedBooking);
	    payment.setAmount(dto.getTotalAmount());
	    payment.setPaymentMode(paymentMode);
	    payment.setPaymentDate(LocalDateTime.now());

	    if(paymentMode.equalsIgnoreCase("CARD") ||
	       paymentMode.equalsIgnoreCase("UPI") ||
	       paymentMode.equalsIgnoreCase("BANK")) {

	        payment.setPaymentStatus("SUCCESS");

	    } else {
	        payment.setPaymentStatus("PENDING");
	    }

	    paymentRepository.save(payment);

	    return "Booking and Payment Successful";
	}
}
	