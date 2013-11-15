package com.prodyna.pac.conference.facility.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.facility.service.RoomService;

@Stateless
public class RoomServiceBean extends AbstractBaseConferenceServiceBean<Room> implements RoomService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	public RoomServiceBean() {
		super(Room.class);
	}

	@Override
	protected EntityManager getEm() {
		return em;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Room> findByLocation(Location location) {
		Query query = em.createNamedQuery(Room.FIND_ROOMS_BY_LOCATION);
		query.setParameter("location", location);
		return query.getResultList();
	}

}
