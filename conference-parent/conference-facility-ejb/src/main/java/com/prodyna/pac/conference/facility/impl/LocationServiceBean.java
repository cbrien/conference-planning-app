package com.prodyna.pac.conference.facility.impl;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@Stateless
@Logged
@Monitored
public class LocationServiceBean extends AbstractBaseConferenceServiceBean<Location> implements LocationService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	@Inject
	Event<Location> locationChanged;

	
	public LocationServiceBean() {
		super(Location.class);
	}

	@Override
	protected EntityManager getEm() {
		return em;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
	
	@Override
	protected Event<Location> getChangedEvent() {
		return locationChanged;
	}
	
	@Override
	@Audited
	public Long add(Location object) throws ConferenceServiceException {
		return super.add(object);
	}
	
	@Override
	@Audited
	public void update(Location object) throws ConferenceServiceException {
		super.update(object);
	}

}
