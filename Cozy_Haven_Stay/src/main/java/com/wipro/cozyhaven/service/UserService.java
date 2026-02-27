package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.LoginRequestDTO;
import com.wipro.cozyhaven.dto.RegisterRequestDTO;
import com.wipro.cozyhaven.dto.UserResponseDTO;

public interface UserService {

	UserResponseDTO register(RegisterRequestDTO request);

	String login(LoginRequestDTO request);

	UserResponseDTO getProfile(Long userId);

	List<?> getMyBookings(Long userId);

	void cancelMyBooking(Long userId, Long bookingId);
}
