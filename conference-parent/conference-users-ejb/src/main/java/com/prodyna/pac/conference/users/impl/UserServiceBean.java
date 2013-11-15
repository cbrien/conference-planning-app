package com.prodyna.pac.conference.users.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.UserService;

@Stateless
public class UserServiceBean extends AbstractBaseConferenceServiceBean<User> implements UserService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByOrganization(Organization organization) {
		Query query = em.createNamedQuery(User.FIND_USERS_BY_ORGANIZATION);
		return query.getResultList();
	}
	
}
