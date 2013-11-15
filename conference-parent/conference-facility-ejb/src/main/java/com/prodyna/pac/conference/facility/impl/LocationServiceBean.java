package com.prodyna.pac.conference.facility.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@Stateless
public class LocationServiceBean extends AbstractBaseConferenceServiceBean<Location> implements LocationService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
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


}
