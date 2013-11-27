package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.richfaces.cdi.push.Push;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;

@Model
public class ConferenceView {

	public static final String PUSH_CDI_TOPIC = "pushCdi";
    
    @Inject
    @Push(topic = PUSH_CDI_TOPIC) Event<String> pushEvent;

	@Inject
	FacesContext facesContext;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

    private Long id;

	private Conference conference = new Conference();

	private List<Talk> talks;

	public void init(ComponentSystemEvent event) {
		conference = conferenceService.get(id);
		talks = talkService.findByConference(conference);
	}

	public void save() {
		try {
			if (conference.getId() == null || conference.getId().longValue() == 0) {
				conference.setId(null);
				conferenceService.add(conference);
				pushEvent.fire(String.format("New conference added: %s (id: %d)", conference.getName(), conference.getId()));
			} else {
				conferenceService.update(conference);
				pushEvent.fire(String.format("Conference saved: %s (id: %d)", conference.getName(), conference.getId()));
			}
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Unable to save conference: "+ e.getMessage(), "Unable to save conference: "+ e.getMessage()));
		}
	}
	
	public void delete() {
		try {
			Long conferenceId = conference.getId();
			if (conferenceId != null && conferenceId.longValue() != 0) {
				conferenceService.delete(conferenceId);
				pushEvent.fire(String.format("Conference deleted: %s (id: %d)", conference.getName(), conferenceId));
			} 
		} catch (Exception e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Unable to save conference: "+ e.getMessage(), "Unable to save conference: "+ e.getMessage()));
		}
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
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}

	/**
	 * @return the talks
	 */
	public List<Talk> getTalks() {
		return talks;
	}

	/**
	 * @param conference the conference to set
	 */
	public void setConference(Conference conference) {
		this.conference = conference;
	}

}
