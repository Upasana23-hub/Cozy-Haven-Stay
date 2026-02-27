package com.wipro.cozyhaven.services;
import java.util.List;

import com.wipro.cozyhaven.entities.Hotel;

public interface IHotelService {

    Hotel addHotel(Long ownerId, Hotel hotel);

    List<Hotel> getHotelByOwner(Long ownerId);

    Hotel updateHotel(Long ownerId, Long hotelId, Hotel updatedhotel);

    void deleteHotel(Long ownerId, Long hotelId);
    
    Hotel  getHotelById(Long hotelId);
    
    List<Hotel> getAllActiveHotels();
    
    List<Hotel> searchHotelsByLocation(String location);
    
    List<Hotel> seachHotelsByLocationAndRating(String location, Double minRating);
}


