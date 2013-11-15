package com.prodyna.pac.conference.users.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.impl.AbstractBaseConferenceServiceBean;
import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.service.OrganizationService;

@Stateless
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

}
