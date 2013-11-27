package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.users.model.Organization;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.OrganizationService;
import com.prodyna.pac.conference.users.service.UserService;

@Model
public class OrganizationView {
	
	private Long id;
	
	private Organization organization;
	
	private List<User> speakers;
	
	@Inject
	private UserService userService;
	
	@Inject
	private OrganizationService organizationService;

	public void init(ComponentSystemEvent event) {
		organization = organizationService.get(id);
		speakers = userService.findByOrganization(organization);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @return the talks
	 */
	public List<User> getSpeakers() {
		return speakers;
	}
	
}
