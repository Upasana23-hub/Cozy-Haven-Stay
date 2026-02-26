package com.wipro.cozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.cozyhaven.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}
