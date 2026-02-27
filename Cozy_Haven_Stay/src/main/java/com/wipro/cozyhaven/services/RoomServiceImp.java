package com.wipro.cozyhaven.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.dtos.RoomDTO;
import com.wipro.cozyhaven.entities.Hotel;
import com.wipro.cozyhaven.entities.Room;
import com.wipro.cozyhaven.exception.ResourceNotFoundException;
import com.wipro.cozyhaven.repositories.HotelRepository;
import com.wipro.cozyhaven.repositories.RoomRepository;

@Service
public class RoomServiceImp implements IRoomService {


	    @Autowired
	    private RoomRepository roomRepository;
	    @Autowired
	    private HotelRepository hotelRepository;

	    
	    @Override
	    public RoomDTO addRoom(RoomDTO roomDTO) {

	        Room room = new Room();
	        room.setRoomNumber(roomDTO.getRoomNumber());
	        room.setRoomType(roomDTO.getRoomType());
	        room.setBedType(roomDTO.getBedType());
	        room.setRoomSize(roomDTO.getRoomSize());
	        room.setMaxPeople(roomDTO.getMaxPeople());
	        room.setBaseFare(roomDTO.getBaseFare());
	        room.setAcAvailable(roomDTO.getAcAvailable());
	        room.setAvailability(true); 
	        room.setAddedAt(LocalDateTime.now());
	        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
	                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

	        room.setHotel(hotel);

	        Room saved = roomRepository.save(room);

	        RoomDTO dto = new RoomDTO();
	        dto.setRoomId(saved.getRoomId());
	        dto.setRoomNumber(saved.getRoomNumber());
	        dto.setRoomType(saved.getRoomType());
	        dto.setBedType(saved.getBedType());
	        dto.setRoomSize(saved.getRoomSize());
	        dto.setMaxPeople(saved.getMaxPeople());
	        dto.setBaseFare(saved.getBaseFare());
	        dto.setAcAvailable(saved.getAcAvailable());
	        dto.setAvailability(saved.getAvailability());
	        dto.setAddedAt(saved.getAddedAt());
	        

	        return dto;
	    }

	   
	    @Override
	    public List<RoomDTO> getAllRooms() {

	        List<Room> rooms = roomRepository.findAll();
	        List<RoomDTO> dtos = new ArrayList<>();

	        for (Room room : rooms) {

	            RoomDTO dto = new RoomDTO();
	            dto.setRoomId(room.getRoomId());
	            dto.setRoomNumber(room.getRoomNumber());
	            dto.setRoomType(room.getRoomType());
	            dto.setBedType(room.getBedType());
	            dto.setRoomSize(room.getRoomSize());
	            dto.setMaxPeople(room.getMaxPeople());
	            dto.setBaseFare(room.getBaseFare());
	            dto.setAcAvailable(room.getAcAvailable());
	            dto.setAvailability(room.getAvailability());
	            dto.setAddedAt(room.getAddedAt());
	            

	            dtos.add(dto);
	        }

	        return dtos;
	    }

	  
	    @Override
	    public RoomDTO getRoomById(int roomId) {

	        Room room = roomRepository.findById(Long.valueOf(roomId))
	                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

	        RoomDTO dto = new RoomDTO();
	        dto.setRoomId(room.getRoomId());
	        dto.setRoomNumber(room.getRoomNumber());
	        dto.setRoomType(room.getRoomType());
	        dto.setBedType(room.getBedType());
	        dto.setRoomSize(room.getRoomSize());
	        dto.setMaxPeople(room.getMaxPeople());
	        dto.setBaseFare(room.getBaseFare());
	        dto.setAcAvailable(room.getAcAvailable());
	        dto.setAvailability(room.getAvailability());
	        dto.setAddedAt(room.getAddedAt());
	        

	        return dto;
	    }

	   
	    @Override
	    public RoomDTO updateRoom(int roomId, RoomDTO roomDTO) {

	        Room room = roomRepository.findById(Long.valueOf(roomId))
	                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

	        room.setRoomNumber(roomDTO.getRoomNumber());
	        room.setRoomType(roomDTO.getRoomType());
	        room.setBedType(roomDTO.getBedType());
	        room.setRoomSize(roomDTO.getRoomSize());
	        room.setMaxPeople(roomDTO.getMaxPeople());
	        room.setBaseFare(roomDTO.getBaseFare());
	        room.setAcAvailable(roomDTO.getAcAvailable());
	        room.setAvailability(roomDTO.getAvailability());

	        Room updated = roomRepository.save(room);

	        RoomDTO dto = new RoomDTO();
	        dto.setRoomId(updated.getRoomId());
	        dto.setRoomNumber(updated.getRoomNumber());
	        dto.setRoomType(updated.getRoomType());
	        dto.setBedType(updated.getBedType());
	        dto.setRoomSize(updated.getRoomSize());
	        dto.setMaxPeople(updated.getMaxPeople());
	        dto.setBaseFare(updated.getBaseFare());
	        dto.setAcAvailable(updated.getAcAvailable());
	        dto.setAvailability(updated.getAvailability());
	        dto.setAddedAt(updated.getAddedAt());
	        
	        return dto;
	    }

	   
	    @Override
	    public void deleteRoom(int roomId) {

	        Room room = roomRepository.findById(Long.valueOf(roomId))
	                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

	        roomRepository.delete(room);
	    }

	    
	    @Override
	    public List<RoomDTO> getAvailableRooms() {

	        List<Room> rooms = roomRepository.findAll();
	        List<RoomDTO> dtos = new ArrayList<>();

	        for (Room room : rooms) {

	            if (room.getAvailability() != null && room.getAvailability()) {

	                RoomDTO dto = new RoomDTO();
	                dto.setRoomId(room.getRoomId());
	                dto.setRoomNumber(room.getRoomNumber());
	                dto.setRoomType(room.getRoomType());
	                dto.setBedType(room.getBedType());
	                dto.setRoomSize(room.getRoomSize());
	                dto.setMaxPeople(room.getMaxPeople());
	                dto.setBaseFare(room.getBaseFare());
	                dto.setAcAvailable(room.getAcAvailable());
	                dto.setAvailability(room.getAvailability());
	                dto.setAddedAt(room.getAddedAt());
	               

	                dtos.add(dto);
	            }
	        }

	        if (dtos.isEmpty()) {
	            throw new ResourceNotFoundException("No available rooms found");
	        }

	        return dtos;
	    }
	}