package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.User;

public interface UserService {

    UserResponseDTO register(UserResponseDTO userDTO);

    String login(String email, String password);

    UserResponseDTO getProfile(Long userId);

    List<Bookings> getMyBookings(Long userId);

    void cancelMyBooking(Long userId, Long bookingId);
    
    User getUserByEmail(String email);
}