package com.wipro.cozyhaven.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.BookingStatus;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Role;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.BookingsRepository;
import com.wipro.cozyhaven.repository.PaymentRepository;
import com.wipro.cozyhaven.repository.ReviewRepository;
import com.wipro.cozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BookingsRepository bookingRepository;
  
    
  @Autowired
  private PasswordEncoder passwordEncoder;

   
    @Override
    public UserResponseDTO register(UserResponseDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
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

    
    @Override
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

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
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 1: Delete payments for each booking
            List<Bookings> bookings = bookingRepository.findByUserUserId(userId);
            for (Bookings booking : bookings) {
                paymentRepository.deleteByBookingBookingId(booking.getBookingId());
            }

            // Step 2: Delete bookings
            bookingRepository.deleteAll(bookings);

            // Step 3: Delete reviews ✅ NEW
            reviewRepository.deleteByUser_UserId(userId);

            // Step 4: Delete user
            userRepository.delete(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong: " + e.getMessage());
        }
    }

   
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