package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;
import com.wipro.cozyhaven.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    
    
    @Autowired
    private UserRepository userRepository;
    

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
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getProfile(@PathVariable Long userId) {
        // Get logged-in user's ID
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Check if the requested userId matches logged-in user
        if (!loggedInUser.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: You can only access your own profile");
        }

        return ResponseEntity.ok(userService.getProfile(userId));
    }

    // ================= MY BOOKINGS =================
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<Bookings>> getMyBookings(@PathVariable Long userId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!loggedInUser.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: You can only view your own bookings");
        }

        return ResponseEntity.ok(userService.getMyBookings(userId));
    }

    // ================= CANCEL BOOKING =================
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{userId}/bookings/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long userId,
            @PathVariable Long bookingId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!loggedInUser.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: You can only cancel your own bookings");
        }

        userService.cancelMyBooking(userId, bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
}