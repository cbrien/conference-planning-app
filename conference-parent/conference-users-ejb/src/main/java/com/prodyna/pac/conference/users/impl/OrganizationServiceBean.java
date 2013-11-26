package com.prodyna.pac.conference.users.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.access.Logged;
import com.prodyna.pac.conference.common.audit.Audited;
import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.common.monitor.Monitored;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.service.OrganizationService;

@Stateless
@Logged
@Monitored
public class OrganizationServiceBean extends AbstractBaseConferenceServiceBean<Organization> implements OrganizationService {

	@Inject
	Logger logger;

	@Inject
	EntityManager em;
	
	public OrganizationServiceBean() {
		super(Organization.class);
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
	@Audited
	public Long add(Organization object) throws ConferenceServiceException {
		return super.add(object);
	}

	@Override
	@Audited
	public void update(Organization object) throws ConferenceServiceException {
		super.update(object);
	}
}
