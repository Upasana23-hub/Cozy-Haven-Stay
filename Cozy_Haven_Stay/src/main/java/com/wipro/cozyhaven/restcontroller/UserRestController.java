package com.wipro.cozyhaven.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserResponseDTO userDTO) {

        return ResponseEntity.ok(userService.register(userDTO));
    }

 
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(
            userService.login(loginDTO.getEmail(), loginDTO.getPassword())
        );
    }


    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UserResponseDTO> getProfile(@PathVariable Long userId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User loggedInUser = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!loggedInUser.getUserId().equals(userId)) {
            return ResponseEntity.status(403)
                    .body(null);
        }

        UserResponseDTO profile = userService.getProfile(userId);

        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
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

    
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
    

    @GetMapping("/getRoleByEmail")
    public ResponseEntity<?> getRoleByEmail(@RequestParam String email) {
        try {
            User user = userService.getUserByEmail(email);
            // return JSON with name and role
            return ResponseEntity.ok(
                Map.of(
                    "role", user.getRole().name(),
                    "name", user.getName()
                )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {

        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        List<UserResponseDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("User and their bookings deleted successfully");
    }
    
}