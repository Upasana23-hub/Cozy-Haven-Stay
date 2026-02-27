package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Bookings;

public interface AdminService {
	
	List<UserResponseDTO> getAllUsers();
	
	void deleteUser(Long userId);
	
	List<Bookings> getAllBookings();
	
	void cancelBooking(Long bookingId);
	
}
