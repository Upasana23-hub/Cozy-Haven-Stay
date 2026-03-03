package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.entity.HotelOwner;

public interface HotelOwnerService {

    HotelOwner createOwner(HotelOwner owner);

    HotelOwner getOwnerByUserId(Long userId);

    List<HotelOwner> getAllOwners();
    HotelOwner approveOwner(Long ownerId);

    HotelOwner activateOwner(Long ownerId);

    List<HotelOwner> getPendingsOwners();

    boolean isOwnerApproved(Long ownerId);

    boolean isOwnerActive(Long ownerId);

    boolean canOwnerCreateHotel(Long ownerId);
}