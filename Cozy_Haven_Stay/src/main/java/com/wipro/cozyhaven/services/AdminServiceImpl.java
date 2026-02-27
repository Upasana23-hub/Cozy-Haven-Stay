package com.wipro.cozyhaven.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dtos.UserResponseDTO;
import com.wipro.cozyhaven.entities.BookingStatus;
import com.wipro.cozyhaven.entities.Bookings;
import com.wipro.cozyhaven.entities.Role;
import com.wipro.cozyhaven.entities.User;
import com.wipro.cozyhaven.repositories.BookingsRepository;
import com.wipro.cozyhaven.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService{
	
	private final UserRepository userRepository;
	private final BookingsRepository bookingsRepository;
	
	
	@Override
	public List<UserResponseDTO> getAllUsers() {
		
		return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long userId) {
		
		  User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (user.getRole() == Role.ADMIN) {
	            throw new RuntimeException("Cannot delete another admin");
	        }

	        userRepository.delete(user);
		
	}

	@Override
	public List<Bookings> getAllBookings() {
		
		return bookingsRepository.findAll();
	}

	@Override
	public void cancelBooking(Long bookingId) {
		
		 Bookings booking = bookingsRepository.findById(bookingId)
	                .orElseThrow(() -> new RuntimeException("Booking not found"));

		 booking.setBookingStatus(BookingStatus.CANCELLED.name());
	        bookingsRepository.save(booking);
		
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
