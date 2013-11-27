package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.common.exceptions.ConferenceServiceException;
import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.facility.model.Location;
import com.prodyna.pac.conference.facility.service.LocationService;

@Model
public class LocationView {

	private Long id;

	private Location location = new Location();

	private List<Conference> conferences;

	@Inject
	private LocationService locationService;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	FacesContext facesContext;

	public void init(ComponentSystemEvent event) {
		location = locationService.get(id);
		conferences = conferenceService.findByLocation(location);
	}

	public void save() {
		try {
			if (location.getId() != null) {
				locationService.add(location);
			} else {
				locationService.update(location);
			}
		} catch (ConferenceServiceException e) {
			facesContext.addMessage("location", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Unable to save location", e.getMessage()));
		}
		facesContext.addMessage("location", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Location saved", location.getName()));
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the conferences
	 */
	public List<Conference> getConferences() {
		return conferences;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
