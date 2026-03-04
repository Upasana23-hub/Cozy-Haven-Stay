package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	@PostMapping("/create")
	public ResponseEntity<BookingsDTO> createBooking(
                                 @Valid @RequestBody BookingsDTO bookingDTO) 
	{

        BookingsDTO savedBooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/getall")
	public ResponseEntity<List<BookingsDTO>> getAllBookings() {
        List<BookingsDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_OWNER','ROLE_ADMIN')")
	@GetMapping("/getbyid/{bookingId}")
	 public ResponseEntity<BookingsDTO> getBookingById(@PathVariable Long bookingId) {
        BookingsDTO booking = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
	@PutMapping("/cancel/{bookingId}")
    public ResponseEntity<BookingsDTO> cancelBooking(@PathVariable Long bookingId) {
        BookingsDTO cancelledBooking = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_OWNER')")
    @GetMapping("/paymentstatus/{paymentStatus}")
    public ResponseEntity<List<BookingsDTO>> getBookingsByPaymentStatus(
            @PathVariable String paymentStatus) {

        List<BookingsDTO> bookings = bookingService.getBookingByPaymentStatus(paymentStatus);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
