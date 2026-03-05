package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.RoomDTO;
import com.wipro.cozyhaven.repository.HotelRepository;
import com.wipro.cozyhaven.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {
	
	@Autowired
	RoomService roomService;
	
	@Autowired 
	HotelRepository hotelRepository;
	
	@PreAuthorize("hasAuthority('ROLE_OWNER')")
	@PostMapping("/add")
	public ResponseEntity<RoomDTO> addRoom(@Valid @RequestBody RoomDTO roomDTO)
	{
        RoomDTO savedRoom = roomService.addRoom(roomDTO);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/getall")
	public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_OWNER','ROLE_ADMIN')")
	@GetMapping("/getbyid/{roomId}")
	public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {
        RoomDTO room = roomService.getRoomById(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN')")
	@PutMapping("/update/{roomId}")
	public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId,
                                       @Valid @RequestBody RoomDTO roomDTO) 
	{

        RoomDTO updatedRoom = roomService.updateRoom(roomId, roomDTO);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/delete/{roomId}")
	 public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) 
	{
		roomService.deleteRoom(roomId);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }
	
	
	@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_OWNER','ROLE_ADMIN')")
	@GetMapping("/available")
	public ResponseEntity<List<RoomDTO>> getAvailableRooms() {
        List<RoomDTO> rooms = roomService.getAvailableRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}
