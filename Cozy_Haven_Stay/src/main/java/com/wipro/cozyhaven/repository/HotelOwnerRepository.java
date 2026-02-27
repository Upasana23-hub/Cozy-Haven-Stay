package com.wipro.cozyhaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.HotelOwner;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {

    Optional<HotelOwner> findByUserId_UserId(Long userId);
    
    List<HotelOwner> findByApprovedFalse();
    
    long countByApprovedTrue();
    
    long countByApprovedFalse();

}