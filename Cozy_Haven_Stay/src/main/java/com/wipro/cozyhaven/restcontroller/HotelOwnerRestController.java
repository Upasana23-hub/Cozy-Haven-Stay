package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.HotelOwnerDTO;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.Role;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;
import com.wipro.cozyhaven.service.HotelOwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owners")
public class HotelOwnerRestController {

	
    private final HotelOwnerService ownerService;
    
    @Autowired
    private UserRepository userRepository;

    public HotelOwnerRestController(HotelOwnerService ownerService, UserRepository userRepository) {
        this.ownerService = ownerService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/add")
    public ResponseEntity<HotelOwner> createOwner(@Valid @RequestBody HotelOwnerDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!user.getRole().equals(Role.OWNER)) {
            throw new RuntimeException("Only users with ROLE_OWNER can create hotel owner record");
        }

        
        HotelOwner owner = new HotelOwner();
        owner.setUser(user); 
        owner.setBuisnessName(dto.getBuisnessName());
        owner.setGstNumber(dto.getGstNumber());
        owner.setAddress(dto.getAddress());

        HotelOwner savedOwner = ownerService.createOwner(owner);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }
    

   
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<HotelOwner> getOwnerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ownerService.getOwnerByUserId(userId));
    }

   
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<List<HotelOwner>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{ownerId}/approve")
    public ResponseEntity<HotelOwner> approveOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(ownerService.approveOwner(ownerId));
    }

   
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{ownerId}/activate")
    public ResponseEntity<HotelOwner> activateOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(ownerService.activateOwner(ownerId));
    }

   
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<HotelOwner>> getPendingOwners() {
        return ResponseEntity.ok(ownerService.getPendingsOwners());
    }

    
    private HotelOwner mapToEntity(HotelOwnerDTO dto) {

        HotelOwner owner = new HotelOwner();
        User user = new User();
        user.setUserId(dto.getUserId());  
        owner.setUser(user);               
        owner.setBuisnessName(dto.getBuisnessName());
        owner.setGstNumber(dto.getGstNumber());
        owner.setAddress(dto.getAddress());

        return owner;
    }
}