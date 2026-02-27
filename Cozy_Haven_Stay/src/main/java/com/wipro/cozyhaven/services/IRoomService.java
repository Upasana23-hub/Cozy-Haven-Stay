package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.RoomDTO;


public interface RoomService {
	
	 RoomDTO addRoom(RoomDTO roomDTO);
	 List<RoomDTO> getAllRooms();
	
	 RoomDTO getRoomById(int roomId);
	
	 RoomDTO updateRoom(int roomId,RoomDTO roomDTO );
	
	 void deleteRoom(int roomId);
	List<RoomDTO> getAvailableRooms( );
	

}
