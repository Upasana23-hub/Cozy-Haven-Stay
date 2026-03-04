package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.dto.RoomDTO;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class RoomServiceImplTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    // Helper method to create a hotel with owner
    private Long createHotel() {
        User user = new User();
        user.setName("Owner");
        user.setEmail("owner@test.com");
        user.setPassword("123");
        user = userRepository.save(user);

        HotelOwner owner = new HotelOwner();
        owner.setUser(user);
        owner.setBuisnessName("Business");
        owner = hotelOwnerRepository.save(owner);

        Hotel hotel = new Hotel();
        hotel.setName("Cozy Haven");
        hotel.setLocation("Chennai");
        hotel.setDescription("Premium stay");
        hotel.setOwner(owner);
        hotel = hotelRepository.save(hotel);

        return hotel.getHotelId();
    }

    @Test
    void testAddRoom() {
        Long hotelId = createHotel();

        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber("CH101");
        dto.setRoomType("DELUXE");
        dto.setBedType("KING");
        dto.setBaseFare(3500.0);
        dto.setAvailability(true);
        dto.setHotelId(hotelId);

        RoomDTO saved = roomService.addRoom(dto);

        assertNotNull(saved.getRoomId());
        assertEquals("DELUXE", saved.getRoomType());
    }

    @Test
    void testGetAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        assertNotNull(rooms);
    }

    @Test
    void testGetRoomById() {
        Long hotelId = createHotel();

        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber("CH102");
        dto.setRoomType("SUITE");
        dto.setBedType("QUEEN");
        dto.setBaseFare(5000.0);
        dto.setAvailability(true);
        dto.setHotelId(hotelId);

        RoomDTO saved = roomService.addRoom(dto);
        RoomDTO found = roomService.getRoomById(saved.getRoomId());

        assertEquals("SUITE", found.getRoomType());
    }

    @Test
    void testUpdateRoom() {
        Long hotelId = createHotel();

        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber("CH103");
        dto.setRoomType("DOUBLE");
        dto.setBedType("DOUBLE");
        dto.setBaseFare(2500.0);
        dto.setAvailability(true);
        dto.setHotelId(hotelId);

        RoomDTO saved = roomService.addRoom(dto);

        saved.setRoomType("EXECUTIVE");
        saved.setBaseFare(4200.0);

        RoomDTO updated = roomService.updateRoom(saved.getRoomId(), saved);

        assertEquals("EXECUTIVE", updated.getRoomType());
        assertEquals(4200.0, updated.getBaseFare());
    }

    @Test
    void testDeleteRoom() {
        Long hotelId = createHotel();

        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber("CH105");
        dto.setRoomType("FAMILY_SUITE");
        dto.setBedType("KING");
        dto.setBaseFare(6500.0);
        dto.setAvailability(true);
        dto.setHotelId(hotelId);

        RoomDTO saved = roomService.addRoom(dto);
        roomService.deleteRoom(saved.getRoomId());

        // Check room list is empty or does not contain this room
        List<RoomDTO> rooms = roomService.getAllRooms();
        assertTrue(rooms.stream().noneMatch(r -> r.getRoomId().equals(saved.getRoomId())));
    }

    @Test
    void testGetAvailableRooms() {
        Long hotelId = createHotel();

        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber("CH104");
        dto.setRoomType("SINGLE");
        dto.setBedType("SINGLE");
        dto.setBaseFare(1500.0);
        dto.setAvailability(true);
        dto.setHotelId(hotelId);

        roomService.addRoom(dto);

        List<RoomDTO> availableRooms = roomService.getAvailableRooms();
        assertTrue(availableRooms.size() > 0);
    }
}