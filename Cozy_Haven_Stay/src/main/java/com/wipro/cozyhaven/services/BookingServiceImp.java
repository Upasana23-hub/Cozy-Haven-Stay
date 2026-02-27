package com.wipro.cozyhaven.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dto.BookingsDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Room;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repository.BookingsRepository;
import com.wipro.cozyhaven.repository.RoomRepository;
import com.wipro.cozyhaven.repository.UserRepository;

@Service
public class BookingServiceImp implements BookingService {

	@Autowired
	private BookingsRepository bookingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RoomRepository roomRepository;

	@Override
	public BookingsDTO createBooking(BookingsDTO bookingDTO) {
		User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> 
                        new ResourceNotFoundException("User not found"));
	
		Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Room not found"));
        Bookings booking = new Bookings();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setHotel(room.getHotel());
        booking.setBookedAt(LocalDateTime.now());
        booking.setCheckIn(bookingDTO.getCheckIn());
        booking.setCheckOut(bookingDTO.getCheckOut());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        booking.setTotalAmount(bookingDTO.getTotalAmount());

        Bookings savedBooking = bookingRepository.save(booking);

        BookingsDTO savedDTO = new BookingsDTO();

        savedDTO.setBookingId(savedBooking.getBookingId());
        savedDTO.setUserId(savedBooking.getUser().getUserId());
        savedDTO.setRoomId(savedBooking.getRoom().getRoomId());
        savedDTO.setCheckIn(savedBooking.getCheckIn());
        savedDTO.setCheckOut(savedBooking.getCheckOut());
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
        dto.setPaymentStatus(updatedBooking.getPaymentStatus());
        dto.setTotalAmount(updatedBooking.getTotalAmount());
        
	
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
}
	