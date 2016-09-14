package com.prodyna.pac.conference.events.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.users.model.User;


@Stateless
@Logged
@Monitored
public class AssignmentServiceBean extends AbstractBaseConferenceServiceBean<Assignment> {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	@Inject
	Event<Assignment> assignmentChanged;

	public AssignmentServiceBean() {
		super(Assignment.class);
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
	protected Event<Assignment> getChangedEvent() {
		return assignmentChanged;
	}
	
	@SuppressWarnings("unchecked")
	public List<Assignment> findBySpeaker(User speaker) {
		Query query = em.createNamedQuery(Assignment.FIND_ASSIGNMENTS_BY_SPEAKER);
		query.setParameter("speaker", speaker);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Assignment> findByTalk(Talk talk) {
		Query query = em.createNamedQuery(Assignment.FIND_ASSIGNMENTS_BY_TALK);
		query.setParameter("talk", talk);
		return query.getResultList();
	}
	
	@Override
	public Long add(Assignment object) throws ConferenceServiceException {
		return super.add(object);
	}
	
	@Override
	public void update(Assignment object) throws ConferenceServiceException {
		super.update(object);
	}

}
