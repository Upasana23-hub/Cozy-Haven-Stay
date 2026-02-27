package com.wipro.cozyhaven.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.cozyhaven.entities.Hotel;
import com.wipro.cozyhaven.entities.HotelOwner;
import com.wipro.cozyhaven.repositories.HotelRepository;


@Service

public class HotelServiceImpl implements IHotelService {

    private final HotelRepository hotelRepository;
    

    public HotelServiceImpl(HotelRepository hotelRepository) {
		super();
		this.hotelRepository = hotelRepository;
	}

	@Override
    public Hotel addHotel(Long ownerId, Hotel hotel) {

        HotelOwner owner = new HotelOwner();
        owner.setOwnerId(ownerId);
        hotel.setOwner(owner);

        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getHotelByOwner(Long ownerId) {
        return hotelRepository.findByOwner_OwnerId(ownerId);
    }

    @Override
    public Hotel updateHotel(Long ownerId, Long hotelId, Hotel updatedHotel) {

        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (!existingHotel.getOwner().getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized access");
        }

        existingHotel.setName(updatedHotel.getName());
        existingHotel.setLocation(updatedHotel.getLocation());
        existingHotel.setDescription(updatedHotel.getDescription());
        existingHotel.setActive(updatedHotel.isActive());

        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteHotel(Long ownerId, Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (!hotel.getOwner().getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Access denied");
        }

        hotelRepository.delete(hotel);
    }

	@Override
	public Hotel getHotelById(Long hotelId) {
		return hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel Not Found"));
	}

	@Override
	public List<Hotel> getAllActiveHotels() {
		return hotelRepository.findByActiveTrue();
	}

	@Override
	public List<Hotel> searchHotelsByLocation(String location) {
		return hotelRepository.findByLocationIgnoreCaseAndActiveTrue(location);
	}

	@Override
	public List<Hotel> seachHotelsByLocationAndRating(String location, Double minRating) {
		return hotelRepository.findByLocationIgnoreCaseAndRatingGreaterThanEqualAndActiveTrue(location, minRating);
	}
	
	
}
