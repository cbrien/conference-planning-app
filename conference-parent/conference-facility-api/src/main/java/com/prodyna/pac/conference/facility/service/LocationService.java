package com.prodyna.pac.conference.facility.service;

import com.prodyna.pac.conference.facility.model.Location;

public interface LocationService {
	
	Location getLocation(long id);
	void addLocation(Location location);
	void updateLocation(Location location);
	void deleteLocation(long id);

}
