package com.wipro.cozyhaven.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.entity.HotelOwner;
import com.wipro.cozyhaven.repository.HotelOwnerRepository;




@Service

public class HotelOwnerServiceImpl implements HotelOwnerService{
	
	private final HotelOwnerRepository ownerRepository;

	public HotelOwnerServiceImpl(HotelOwnerRepository ownerRepository) {
		super();
		this.ownerRepository = ownerRepository;
	}

	@Override
	public HotelOwner createOwner(HotelOwner owner) {
		return ownerRepository.save(owner);
		
	}

	@Override
	public HotelOwner getOwnerByUserId(Long ownerId) {
		return ownerRepository.findById(ownerId).orElseThrow();
	}
	

	@Override
	public List<HotelOwner> getAllOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public HotelOwner approvedOwner(Long ownerId) {
		HotelOwner owner= getOwnerByUserId(ownerId);
		return ownerRepository.save(owner);
	}

	@Override
	public HotelOwner activateOwner(Long ownerId) {
		HotelOwner owner= getOwner(ownerId);
		owner.setActive(true);
		return ownerRepository.save(owner);
	}

	@Override
	public List<HotelOwner> getPendingsOwners() {
		return ownerRepository.findByApprovedFalse();
	}

	@Override
	public boolean isOwnerApproved(Long ownerId) {
		return getOwner(ownerId).isApproved();
	}

	@Override
	public boolean isownerActive(Long ownerId) {
		return getOwner(ownerId).isActive();
	}
	private HotelOwner getOwner(Long ownerId) {
		return ownerRepository.findByUserId_UserId(ownerId).orElseThrow(() -> new RuntimeException("Hotel owner not Found"));
	}
	
	
	

}
