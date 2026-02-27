package com.wipro.cozyhaven.services;

import java.util.List;

import com.wipro.cozyhaven.dtos.RoomDTO;


public interface IRoomService {
	
	 RoomDTO addRoom(RoomDTO roomDTO);
	 List<RoomDTO> getAllRooms();
	
	 RoomDTO getRoomById(int roomId);
	
	 RoomDTO updateRoom(int roomId,RoomDTO roomDTO );
	
	 void deleteRoom(int roomId);
	List<RoomDTO> getAvailableRooms( );
	

}
