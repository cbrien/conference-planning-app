package com.prodyna.pac.conference.events.service;

import java.util.List;

import com.prodyna.pac.conference.common.service.BaseConferenceService;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.facility.model.Location;

public interface ConferenceService extends BaseConferenceService<Conference> {

	List<Conference> findByLocation(Location location);
	
}
