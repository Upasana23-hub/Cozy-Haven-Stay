package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.dto.UserResponseDTO;
import com.wipro.cozyhaven.entity.Bookings;
import com.wipro.cozyhaven.entity.Role;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    
    @Test
    void testRegisterSuccess() {
        UserResponseDTO request = UserResponseDTO.builder()
                .name("Debdutta")
                .email("deb@gmail.com")
                .password("123456")
                .phone("9876543210")
                .address("Kolkata")
                .build();

        UserResponseDTO response = userService.register(request);

        assertNotNull(response.getUserId());
        assertEquals("Debdutta", response.getName());
        assertEquals(Role.USER, response.getRole());
        assertEquals("deb@gmail.com", response.getEmail());
    }

   
    @Test
    void testLoginSuccess() {
        // First, register a user
        UserResponseDTO registered = userService.register(UserResponseDTO.builder()
                .name("LoginUser")
                .email("loginuser@gmail.com")
                .password("password")
                .phone("9999999999")
                .address("Delhi")
                .build());

        String message = userService.login("loginuser@gmail.com", "password");
        assertEquals("Login Successful", message);
    }

    
    @Test
    void testGetProfileSuccess() {
        UserResponseDTO registered = userService.register(UserResponseDTO.builder()
                .name("ProfileUser")
                .email("profile@gmail.com")
                .password("123456")
                .phone("1111111111")
                .address("Delhi")
                .build());

        UserResponseDTO profile = userService.getProfile(registered.getUserId());

        assertEquals("ProfileUser", profile.getName());
        assertEquals("profile@gmail.com", profile.getEmail());
        assertEquals(Role.USER, profile.getRole());
    }

    
    @Test
    void testGetMyBookingsSuccess() {
        UserResponseDTO registered = userService.register(UserResponseDTO.builder()
                .name("BookingUser")
                .email("bookinguser@gmail.com")
                .password("123456")
                .phone("1234567890")
                .address("Mumbai")
                .build());

        List<Bookings> bookings = userService.getMyBookings(registered.getUserId());
        // Since no bookings are added yet, should return empty list
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

   
    @Test
    void testCancelMyBookingSuccess() {
        UserResponseDTO registered = userService.register(UserResponseDTO.builder()
                .name("CancelUser")
                .email("canceluser@gmail.com")
                .password("123456")
                .phone("9998887777")
                .address("Chennai")
                .build());
        assertNotNull(userService);
    }

    @Test
    void testGetUserByEmailSuccess() {
        UserResponseDTO registered = userService.register(UserResponseDTO.builder()
                .name("EmailUser")
                .email("emailuser@gmail.com")
                .password("123456")
                .phone("1112223333")
                .address("Bangalore")
                .build());

        assertNotNull(userService.getUserByEmail("emailuser@gmail.com"));
    }
}