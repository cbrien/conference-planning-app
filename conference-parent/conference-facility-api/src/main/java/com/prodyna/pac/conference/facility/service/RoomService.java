package com.prodyna.pac.conference.facility.service;

import java.util.List;

import com.prodyna.pac.conference.facility.model.Room;

public interface RoomService {
	
	Room getRoom(long id);
	Room addRoom(Room Room);
	void updateRoom(Room Room);
	void deleteRoom(long id);
	List<Room> findRoomsByLocationId(long locationId);

}
