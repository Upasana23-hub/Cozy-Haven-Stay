package com.wipro.cozyhaven.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.cozyhaven.dto.HotelOwnerDTO;
import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.service.HotelOwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owners")
public class HotelOwnerRestController {
	
	@Autowired
	public final HotelOwnerService ownerService;

	public HotelOwnerRestController(HotelOwnerService ownerService) {
		super();
		this.ownerService = ownerService;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/add")
	public ResponseEntity<HotelOwner> createOwner(@Valid @RequestBody HotelOwnerDTO dto) {
		HotelOwner owner = mapToEntity(dto);
		HotelOwner savedOwner = ownerService.createOwner(owner);
		return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLE_OWNER','ROLE_ADMIN')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<HotelOwner> getOwnerByUserId(@PathVariable Long userId) {
		HotelOwner owner = ownerService.getOwnerByUserId(userId);
		return ResponseEntity.ok(owner);
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<HotelOwner>> getAllOwners() {
		return ResponseEntity.ok(ownerService.getAllOwners());
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{ownerId}/approve")
    public ResponseEntity<HotelOwner> approveOwner(@PathVariable Long ownerId) {
		return ResponseEntity.ok(ownerService.approvedOwner(ownerId));
	
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
	        owner.setBuisnessName(dto.getBuisnessName());
	        owner.setGstNumber(dto.getGstNumber());
	        owner.setAddress(dto.getAddress());
	        return owner;
	    }
	 
	 

	

}
