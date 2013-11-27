package com.prodyna.pac.conference.users.impl;

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
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.UserService;

@Stateless
@Logged
@Monitored
public class UserServiceBean extends AbstractBaseConferenceServiceBean<User> implements UserService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	@Inject
	Event<User> userChanged;

	public UserServiceBean() {
		super(User.class);
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
	protected Event<User> getChangedEvent() {
		return userChanged;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByOrganization(Organization organization) {
		Query query = em.createNamedQuery(User.FIND_USERS_BY_ORGANIZATION);
		query.setParameter("organization", organization);
		return query.getResultList();
	}
	
	@Override
	@Audited
	public Long add(User object) throws ConferenceServiceException {
		return super.add(object);
	}
	
	@Override
	@Audited
	public void update(User object) throws ConferenceServiceException {
		super.update(object);
	}
	
}
