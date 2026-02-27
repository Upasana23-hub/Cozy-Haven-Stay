package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.BookingsDTO;


public interface IBookingService {
	
	 BookingsDTO createBooking(BookingsDTO bookingDTO);
	 List<BookingsDTO> getAllBookings();
	
	 BookingsDTO getBookingById(Long bookingId);
     BookingsDTO  cancelBooking(Long bookingId);
    
     List<BookingsDTO> getBookingByPaymentStatus(String paymentStatus);

}
