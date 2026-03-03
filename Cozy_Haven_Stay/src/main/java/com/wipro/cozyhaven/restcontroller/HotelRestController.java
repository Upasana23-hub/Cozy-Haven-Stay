package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.HotelDTO;
import com.wipro.cozyhaven.entity.Hotel;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.service.HotelOwnerService;
import com.wipro.cozyhaven.service.HotelService;
import com.wipro.cozyhaven.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
@Validated
public class HotelRestController {

    private final HotelService hotelService;
    private final HotelOwnerService hotelOwnerService;
    private final UserService userService;

    public HotelRestController(HotelService hotelService, HotelOwnerService hotelOwnerService, UserService userService) {
        this.hotelService = hotelService;
        this.hotelOwnerService = hotelOwnerService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/add")
    public ResponseEntity<Hotel> addHotel(@Valid @RequestBody HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setRating(dto.getRating());
        hotel.setActive(true);

        
        HotelOwner owner = new HotelOwner();
        owner.setOwnerId(dto.getOwnerId());
        hotel.setOwner(owner);

        Hotel savedHotel = hotelService.addHotel(dto.getOwnerId(), hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }



    @GetMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);

        // check if the logged-in user is the owner
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName(); 

        if (!hotel.getOwner().getUser().getEmail().equals(loggedInEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Map to DTO
        HotelDTO dto = new HotelDTO();
        dto.setHotelId(hotel.getHotelId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setLocation(hotel.getLocation());
        dto.setRating(hotel.getRating());
        dto.setActive(hotel.isActive());
        dto.setOwnerId(hotel.getOwner().getOwnerId());

        return ResponseEntity.ok(dto);
    }
    

   
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN')")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Hotel> getHotelByOwner(@PathVariable Long ownerId, Authentication auth) {

        User user = userService.getUserByEmail(auth.getName());
        if (user.getRole().equals("OWNER") && !user.getUserId().equals(hotelOwnerService.getOwnerByUserId(user.getUserId()).getUser().getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Hotel hotel = hotelService.getHotelByOwner(ownerId).stream().findFirst().orElseThrow(() -> new RuntimeException("Hotel not found"));
        return ResponseEntity.ok(hotel);
    }


    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/{hotelId}/owner/{ownerId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long ownerId, @PathVariable Long hotelId, @Valid @RequestBody HotelDTO dto) {
        Hotel updateHotel = new Hotel();
        updateHotel.setName(dto.getName());
        updateHotel.setLocation(dto.getLocation());
        updateHotel.setDescription(dto.getDescription());
        updateHotel.setActive(dto.isActive());

        return ResponseEntity.ok(hotelService.updateHotel(ownerId, hotelId, updateHotel));
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/{hotelId}/owner/{ownerId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long ownerId, @PathVariable Long hotelId) {
        hotelService.deleteHotel(ownerId, hotelId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search/{location}")
    public ResponseEntity<List<Hotel>> searchHotelsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(hotelService.searchHotelsByLocation(location));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<List<Hotel>> getAllActiveHotels() {
        return ResponseEntity.ok(hotelService.getAllActiveHotels());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search/filter/{location}/{minRating}")
    public ResponseEntity<List<Hotel>> searchHotelsByLocationAndRating(
            @PathVariable String location,
            @PathVariable Double minRating) {
        return ResponseEntity.ok(hotelService.searchHotelsByLocationAndRating(location, minRating));
    }
}