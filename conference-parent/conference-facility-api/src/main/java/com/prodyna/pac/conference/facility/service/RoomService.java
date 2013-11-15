package com.prodyna.pac.conference.facility.service;

import java.util.List;

import com.prodyna.pac.conference.common.service.BaseConferenceService;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.model.Room;

public interface RoomService extends BaseConferenceService<Room> {

	List<Room> findByLocation(Location location);

}
