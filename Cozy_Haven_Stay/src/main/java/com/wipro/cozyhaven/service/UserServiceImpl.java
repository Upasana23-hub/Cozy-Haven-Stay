package com.wipro.cozyhaven.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.BookingStatus;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Role;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.BookingsRepository;
import com.wipro.cozyhaven.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookingsRepository bookingRepository;

    // ================= REGISTER =================
    @Override
    public UserResponseDTO register(UserResponseDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .role(
                    userDTO.getRole() != null 
                    ? userDTO.getRole() 
                    : Role.USER
                )
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    // ================= LOGIN =================
    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return "Login Successful";
    }

    // ================= PROFILE =================
    @Override
    public UserResponseDTO getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    // ================= MY BOOKINGS =================
    @Override
    public List<Bookings> getMyBookings(Long userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    // ================= CANCEL BOOKING =================
    @Override
    public void cancelMyBooking(Long userId, Long bookingId) {

        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized action");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED.name());
        bookingRepository.save(booking);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    // ================= MAPPER =================
    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}