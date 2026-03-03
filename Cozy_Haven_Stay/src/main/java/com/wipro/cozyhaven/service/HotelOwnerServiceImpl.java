package com.wipro.cozyhaven.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelOwnerServiceImpl implements HotelOwnerService {

    private final HotelOwnerRepository ownerRepository;

    // ================= CREATE OWNER =================
    @Override
    public HotelOwner createOwner(HotelOwner owner) {
        return ownerRepository.save(owner);
    }

    // ================= GET OWNER BY USER ID =================
    @Override
    public HotelOwner getOwnerByUserId(Long userId) {
        return ownerRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Hotel owner not found for userId: " + userId));
    }

    // ================= GET ALL OWNERS =================
    @Override
    public List<HotelOwner> getAllOwners() {
        return ownerRepository.findAll();
    }

    // ================= APPROVE OWNER (ADMIN) =================
    @Override
    public HotelOwner approveOwner(Long ownerId) {
        HotelOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        owner.setApproved(true);
        return ownerRepository.save(owner);
    }

    // ================= ACTIVATE OWNER =================
    @Override
    public HotelOwner activateOwner(Long ownerId) {
        HotelOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        owner.setActive(true);
        return ownerRepository.save(owner);
    }

    // ================= PENDING OWNERS =================
    @Override
    public List<HotelOwner> getPendingsOwners() {
        return ownerRepository.findByApprovedFalse();
    }

    // ================= CHECK APPROVAL =================
    @Override
    public boolean isOwnerApproved(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"))
                .isApproved();
    }

    // ================= CHECK ACTIVE =================
    @Override
    public boolean isOwnerActive(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"))
                .isActive();
    }

    // ================= CAN OWNER CREATE HOTEL =================
    @Override
    public boolean canOwnerCreateHotel(Long ownerId) {
        HotelOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return owner.isApproved() && owner.isActive();
    }
}