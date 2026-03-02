package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.BookingsDTO;
import com.wipro.cozyhaven.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/create")
	public ResponseEntity<BookingsDTO> createBooking(
                                 @Valid @RequestBody BookingsDTO bookingDTO) 
	{

        BookingsDTO savedBooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

	@GetMapping("/getall")
	public ResponseEntity<List<BookingsDTO>> getAllBookings() {
        List<BookingsDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
	
	@GetMapping("/getbyid/{id}")
	 public ResponseEntity<BookingsDTO> getBookingById(@PathVariable Long bookingId) {
        BookingsDTO booking = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
	
	@PutMapping("/cancel/{id}")
    public ResponseEntity<BookingsDTO> cancelBooking(@PathVariable Long bookingId) {
        BookingsDTO cancelledBooking = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
    }
	
	@GetMapping("/payment/{status}")
    public ResponseEntity<List<BookingsDTO>> getBookingByPaymentStatus(
            @PathVariable String paymentStatus) {

        List<BookingsDTO> bookings = bookingService.getBookingByPaymentStatus(paymentStatus);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
