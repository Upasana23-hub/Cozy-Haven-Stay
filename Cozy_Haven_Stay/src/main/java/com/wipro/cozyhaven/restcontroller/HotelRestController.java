package com.wipro.cozyhaven.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wipro.cozyhaven.dto.HotelDTO;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.service.HotelService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
@Validated
public class HotelRestController {
	
	@Autowired
	private final HotelService hotelService;

	public HotelRestController(HotelService hotelService) {
		super();
		this.hotelService = hotelService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Hotel> addHotel(@Valid @RequestBody HotelDTO dto) {
		Hotel hotel = new Hotel();
		hotel.setName(dto.getName());
		hotel.setLocation(dto.getLocation());
		hotel.setDescription(dto.getDescription());
		hotel.setRating(dto.getRating());
		hotel.setActive(true);
		
		HotelOwner owner= new HotelOwner();
		owner.setOwnerId(dto.getOwnerId());
		hotel.setOwner(owner);
		
		Hotel savedHotel = hotelService.addHotel(dto.getOwnerId(), hotel);
		return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
	}
	
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
		return ResponseEntity.ok(hotelService.getHotelById(hotelId));
	}
	
	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<List<Hotel>> getHotelByOwner(@PathVariable Long ownerId) {
		return ResponseEntity.ok(hotelService.getHotelByOwner(ownerId));
	}
	
	@PutMapping("/{hotelId}/owner/{ownerId}")
	public ResponseEntity<Hotel> updateHotel(@PathVariable Long ownerId, @PathVariable Long hotelId, @Valid @RequestBody HotelDTO dto) {
		Hotel updateHotel = new Hotel();
		updateHotel.setName(dto.getName());
		updateHotel.setLocation(dto.getLocation());
		updateHotel.setDescription(dto.getDescription());
		updateHotel.setActive(dto.isActive());
		
		return ResponseEntity.ok(hotelService.updateHotel(ownerId, hotelId, updateHotel));
	}
	
	@DeleteMapping("/{hotelId}/owner/{ownerId}")
	public ResponseEntity<Void> deleteHotel(@PathVariable Long ownerId, @PathVariable Long hotelId) {
		hotelService.deleteHotel(ownerId, hotelId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Hotel>> searchHotelsByLocation(@RequestParam String location) {
		return ResponseEntity.ok(hotelService.searchHotelsByLocation(location));
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<Hotel>> getAllActiveHotels() {
		return ResponseEntity.ok(hotelService.getAllActiveHotels());
	}
	
	@GetMapping("/search/filter")
	public ResponseEntity<List<Hotel>> searchHotelsByLocationAndRating(@RequestParam String location, @RequestParam Double minRating) {
		return ResponseEntity.ok(hotelService.seachHotelsByLocationAndRating(location, minRating));
	}
	

}
