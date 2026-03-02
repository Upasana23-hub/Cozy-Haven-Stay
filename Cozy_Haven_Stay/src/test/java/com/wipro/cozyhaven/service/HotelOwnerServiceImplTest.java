package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
class HotelOwnerServiceImplTest {

	@Autowired
	private HotelOwnerService ownerService;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void testCreateOwner() {
		HotelOwner owner = createOwner(1999L);
		assertNotNull(owner.getOwnerId());
		
	}
	
	@Test
	void testGetOwnerByUserId() {
		HotelOwner owner = createOwner(2001L);
		HotelOwner fetched = ownerService.getOwnerByUserId(owner.getUserId().getUserId());
		assertEquals(owner.getOwnerId(), fetched.getOwnerId());
	}
	
	@Test
	void tstGetAllOwners() {
		createOwner(2002L);
		List<HotelOwner> owners= ownerService.getAllOwners();
		assertFalse(owners.isEmpty());
	}
	
	 @Test
	 void testApproveOwner() {
	     HotelOwner owner = createOwner(2003L);
	     HotelOwner approved = ownerService.approvedOwner(owner.getOwnerId());
	     assertTrue(approved.isApproved());
	}
	 
	 @Test
	 void testActiveOwner() {
		 HotelOwner owner = createOwner(2004L);
		 owner.setActive(false);
		 HotelOwner activated = ownerService.activateOwner(owner.getOwnerId());
		 assertTrue(activated.isActive());
	 }
	 
	 @Test
	 void testGetPendingOwners() {
		 createOwner(2005L);
	     List<HotelOwner> pending = ownerService.getPendingsOwners();
	     assertNotNull(pending);
	 }
	 
	 @Test
	 void testIsOwnerActive() {
		 HotelOwner owner= createOwner(2006L);
		 assertTrue(ownerService.isownerActive(owner.getOwnerId()));
		 
	 }
	 
	 @Test
	 void testIsOwnerApproved() {
		 HotelOwner owner = createOwner(2007L);
		 assertFalse(ownerService.isOwnerApproved(owner.getOwnerId()));
	 
	 }
	 
	 @Test
	 void testCanOwnerCreateHotel() {
	     HotelOwner owner = createOwner(1009L);
	     ownerService.approvedOwner(owner.getOwnerId());
	     assertTrue(ownerService.canOwnerCreateHotel(owner.getOwnerId()));
	    }
	 
	 private HotelOwner createOwner(Long userId) {
		    // 1️⃣ Persist a User first
		    User user = new User();
		    user.setName("Test User " + userId);
		    user.setEmail("test@example.com");
		    user.setPassword("Test");
		    user = userRepository.save(user);  

		   
		    HotelOwner owner = new HotelOwner();
		    owner.setUserId(user); 
		    owner.setBuisnessName("JW Marriot");
		    owner.setGstNumber("GST" + userId);
		    owner.setAddress("Kolkata");

		    return ownerService.createOwner(owner);
		}

}
