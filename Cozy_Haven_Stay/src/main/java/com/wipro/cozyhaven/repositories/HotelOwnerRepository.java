package com.wipro.cozyhaven.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entities.HotelOwner;

import java.util.List;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {

    Optional<HotelOwner> findByUserId_UserId(Long userId);
    
    List<HotelOwner> findByApprovedFalse();

}