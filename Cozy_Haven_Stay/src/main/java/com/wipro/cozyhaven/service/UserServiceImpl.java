package com.wipro.cozyhaven.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dto.LoginRequestDTO;
import com.wipro.cozyhaven.dto.RegisterRequestDTO;
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

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public String login(LoginRequestDTO request) {

        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        return "Login Successful";
    }

    @Override
    public UserResponseDTO getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<Bookings> getMyBookings(Long userId) {
    	return bookingRepository.findByUserUserId(userId);
    }

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

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}