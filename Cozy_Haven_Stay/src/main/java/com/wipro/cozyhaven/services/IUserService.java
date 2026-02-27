package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.LoginRequestDTO;
import com.wipro.cozyhaven.dtos.RegisterRequestDTO;
import com.wipro.cozyhaven.dtos.UserResponseDTO;

public interface IUserService {

	UserResponseDTO register(RegisterRequestDTO request);

	String login(LoginRequestDTO request);

	UserResponseDTO getProfile(Long userId);

	List<?> getMyBookings(Long userId);

	void cancelMyBooking(Long userId, Long bookingId);
}
