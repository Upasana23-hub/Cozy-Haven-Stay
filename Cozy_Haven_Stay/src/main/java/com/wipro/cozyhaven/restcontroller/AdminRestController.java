package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.service.AdminService;

@RestController
@RequestMapping("/api/admin")

public class AdminRestController {

	@Autowired
	private AdminService adminService;

	// 1. Get All Users
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		return ResponseEntity.ok(adminService.getAllUsers());
	}

	// 2. Delete User
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> getAllUser(@PathVariable Long userId) {
		adminService.deleteUser(userId);
		return ResponseEntity.ok("User deleted successfully");
	}

	// 3. Get All Bookings
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/bookings")
	public ResponseEntity<List<Bookings>> getAllBookings() {
		return ResponseEntity.ok(adminService.getAllBookings());
	}

	// 4. Cancel Bookings
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/bookings/{bookingId}/cancel")
	public ResponseEntity<String> cancelBookings(@PathVariable Long bookingId) {
		adminService.cancelBooking(bookingId);
		return ResponseEntity.ok("Booking cancelled by admin");
	}

}
