package com.prodyna.pac.conference.events.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.facility.model.Location;

@Stateless
@Logged
@Monitored
public class ConferenceServiceBean extends AbstractBaseConferenceServiceBean<Conference> implements ConferenceService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	public ConferenceServiceBean() {
		super(Conference.class);
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
	public List<Conference> findByLocation(Location location) {
		Query query = em.createNamedQuery(Conference.FIND_CONFERENCE_BY_LOCATION);
		query.setParameter("location", location);
		return query.getResultList();
	}

	@Audited
	@Override
	public Long add(Conference object) throws ConferenceServiceException {
		return super.add(object);
	}
	
	@Audited
	@Override
	public void update(Conference object) throws ConferenceServiceException {
		super.update(object);
	}

}
