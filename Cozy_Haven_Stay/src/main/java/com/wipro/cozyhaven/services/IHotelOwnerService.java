package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.entities.HotelOwner;

public interface IHotelOwnerService {
	HotelOwner createOwner(HotelOwner owner);
	HotelOwner getOwnerByUserId(Long ownerId);
	List<HotelOwner> getAllOwners();
	HotelOwner approvedOwner(Long ownerId);
	HotelOwner activateOwner(Long ownerId);
	List<HotelOwner> getPendingsOwners();
	boolean isOwnerApproved(Long ownerId);
	boolean isownerActive(Long ownerId);

}
