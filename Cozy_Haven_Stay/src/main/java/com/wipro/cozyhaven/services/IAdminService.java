package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.UserResponseDTO;
import com.wipro.cozyhaven.entities.Bookings;

public interface IAdminService {
	
	List<UserResponseDTO> getAllUsers();
	
	void deleteUser(Long userId);
	
	List<Bookings> getAllBookings();
	
	void cancelBooking(Long bookingId);
	
}
