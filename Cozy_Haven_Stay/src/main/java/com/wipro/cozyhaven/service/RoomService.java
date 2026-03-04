package com.wipro.cozyhaven.service;

import java.util.List;

import com.wipro.cozyhaven.dto.RoomDTO;


public interface RoomService {
	
	 RoomDTO addRoom(RoomDTO roomDTO);
	 List<RoomDTO> getAllRooms();
	
	 RoomDTO getRoomById(Long roomId);
	
	 RoomDTO updateRoom(Long roomId,RoomDTO roomDTO );
	
	 void deleteRoom(Long roomId);
	List<RoomDTO> getAvailableRooms( );
	void deleteRoomByUser(Long roomId, Long userId, String role);

}
