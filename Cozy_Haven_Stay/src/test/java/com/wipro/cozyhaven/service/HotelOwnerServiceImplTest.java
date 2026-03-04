package com.wipro.cozyhaven.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.entity.User;
import com.wipro.cozyhaven.repository.UserRepository;

@SpringBootTest
@Transactional
class HotelOwnerServiceImplTest {

    @Autowired
    private HotelOwnerService ownerService;

    @Autowired
    private UserRepository userRepository;

  
    @Test
    void testCreateOwner() {
        HotelOwner owner = createOwner(1001L);
        assertNotNull(owner.getOwnerId());
        assertEquals("JW Marriot", owner.getBuisnessName());
    }

    
    @Test
    void testGetOwnerByUserId() {
        HotelOwner owner = createOwner(1002L);
        HotelOwner fetched = ownerService.getOwnerByUserId(owner.getUser().getUserId());
        assertEquals(owner.getOwnerId(), fetched.getOwnerId());
    }

   
    @Test
    void testGetAllOwners() {
        createOwner(1003L);
        List<HotelOwner> owners = ownerService.getAllOwners();
        assertFalse(owners.isEmpty());
    }

    
    @Test
    void testApproveOwner() {
        HotelOwner owner = createOwner(1004L);
        HotelOwner approved = ownerService.approveOwner(owner.getOwnerId());
        assertTrue(approved.isApproved());
    }

    
    @Test
    void testActivateOwner() {
        HotelOwner owner = createOwner(1005L);
        owner.setActive(false);
        HotelOwner activated = ownerService.activateOwner(owner.getOwnerId());
        assertTrue(activated.isActive());
    }

    
    @Test
    void testGetPendingOwners() {
        createOwner(1006L);
        List<HotelOwner> pending = ownerService.getPendingsOwners();
        assertFalse(pending.isEmpty());
    }

 
    @Test
    void testIsOwnerApproved() {
        HotelOwner owner = createOwner(1007L);
        assertFalse(ownerService.isOwnerApproved(owner.getOwnerId())); 
    }

   
    @Test
    void testIsOwnerActive() {
        HotelOwner owner = createOwner(1008L);
        owner.setActive(true); // mark active
        assertTrue(ownerService.isOwnerActive(owner.getOwnerId()));
    }

   
    @Test
    void testCanOwnerCreateHotel() {
        HotelOwner owner = createOwner(1009L);
        ownerService.approveOwner(owner.getOwnerId());
        ownerService.activateOwner(owner.getOwnerId());
        assertTrue(ownerService.canOwnerCreateHotel(owner.getOwnerId()));
    }

   
    private HotelOwner createOwner(Long userSuffix) {
        User user = new User();
        user.setName("Test User " + userSuffix);
        user.setEmail("test" + userSuffix + "@example.com");
        user.setPassword("TestPass");
        user = userRepository.save(user);

        HotelOwner owner = new HotelOwner();
        owner.setUser(user); // link the user
        owner.setBuisnessName("JW Marriot");
        owner.setGstNumber("GST" + userSuffix);
        owner.setAddress("Kolkata");

        return ownerService.createOwner(owner);
    }
}