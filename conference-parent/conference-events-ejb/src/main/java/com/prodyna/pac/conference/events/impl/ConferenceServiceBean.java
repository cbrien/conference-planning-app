package com.prodyna.pac.conference.events.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.events.exceptions.LocationOccupiedException;
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
	
	@Inject
	Event<Conference> conferenceChanged;
	
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
	
	@Override
	protected Event<Conference> getChangedEvent() {
		return conferenceChanged;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Conference> findByLocation(Location location) {
		Query query = em.createNamedQuery(Conference.FIND_CONFERENCE_BY_LOCATION);
		query.setParameter("location", location);
		return query.getResultList();
	}

	@Override
	@Audited
	public Long add(Conference conference) throws ConferenceServiceException {
		return super.add(isValid(conference));
	}
	
	@Override
	@Audited
	public void update(Conference object) throws ConferenceServiceException {
		super.update(isValid(object));
	}
	
	private Conference isValid(Conference conference) {
		for (Conference existingConference : findByLocation(conference.getLocation())){
			if (!existingConference.getId().equals(conference.getId()) && isOverlapping(conference, existingConference)) {
				throw new LocationOccupiedException(existingConference);
			}
		}
		return conference;
	}

	private boolean isOverlapping(Conference conference, Conference conference2) {
		long start = conference.getStartDate().getTime();
		long end = conference.getEndDate().getTime();
		long start2 = conference2.getStartDate().getTime();
		long end2 = conference2.getEndDate().getTime();
		return ((start < end2) && (end > start2));
	}
}
