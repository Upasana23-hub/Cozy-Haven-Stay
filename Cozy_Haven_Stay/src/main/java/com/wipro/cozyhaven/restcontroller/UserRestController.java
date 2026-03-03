package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.LoginRequestDTO;
import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserResponseDTO userDTO) {

        return ResponseEntity.ok(userService.register(userDTO));
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(
            userService.login(loginDTO.getEmail(), loginDTO.getPassword())
        );
    }

    // ================= GET PROFILE =================
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getProfile(
            @PathVariable Long userId) {

        return ResponseEntity.ok(userService.getProfile(userId));
    }

    // ================= MY BOOKINGS =================
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<Bookings>> getMyBookings(
            @PathVariable Long userId) {

        return ResponseEntity.ok(userService.getMyBookings(userId));
    }

    // ================= CANCEL BOOKING =================
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}/bookings/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long userId,
            @PathVariable Long bookingId) {

        userService.cancelMyBooking(userId, bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}