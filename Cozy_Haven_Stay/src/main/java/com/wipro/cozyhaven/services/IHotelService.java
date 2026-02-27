package com.wipro.cozyhaven.service;
import java.util.List;

import com.wipro.cozyhaven.entity.Hotel;

public interface HotelService {

    Hotel addHotel(Long ownerId, Hotel hotel);

    List<Hotel> getHotelByOwner(Long ownerId);

    Hotel updateHotel(Long ownerId, Long hotelId, Hotel updatedhotel);

    void deleteHotel(Long ownerId, Long hotelId);
    
    Hotel  getHotelById(Long hotelId);
    
    List<Hotel> getAllActiveHotels();
    
    List<Hotel> searchHotelsByLocation(String location);
    
    List<Hotel> seachHotelsByLocationAndRating(String location, Double minRating);
}


