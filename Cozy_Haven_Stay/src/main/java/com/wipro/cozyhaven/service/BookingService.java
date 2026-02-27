package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.BookingsDTO;


public interface BookingService {
	
	 BookingsDTO createBooking(BookingsDTO bookingDTO);
	 List<BookingsDTO> getAllBookings();
	
	 BookingsDTO getBookingById(Long bookingId);
     BookingsDTO  cancelBooking(Long bookingId);
    
     List<BookingsDTO> getBookingByPaymentStatus(String paymentStatus);

}
