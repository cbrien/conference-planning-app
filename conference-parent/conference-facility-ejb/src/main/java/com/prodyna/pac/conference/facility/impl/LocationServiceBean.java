package com.prodyna.pac.conference.facility.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@Stateless
public class LocationServiceBean implements LocationService{
	
	@Inject
	Logger logger;
	
	@Inject
	EntityManager em;

	@Override
	public Location getLocation(long id) {
		return em.find(Location.class, id);
	}

	@Override
	public void addLocation(Location location) {
		em.persist(location);
	}

	@Override
	public void updateLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLocation(long id) {
		// TODO Auto-generated method stub
		
	}

}
