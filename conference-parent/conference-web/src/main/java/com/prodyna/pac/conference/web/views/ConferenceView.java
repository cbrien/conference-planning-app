package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.events.model.Conference;
import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.ConferenceService;
import com.prodyna.pac.conference.events.service.TalkService;

@Model
public class ConferenceView {
	
	private Long id;
	
	private Conference conference;
	
	private List<Talk> talks;
	
	@Inject
	private ConferenceService conferenceService;
	
	@Inject
	private TalkService talkService;

	public void init(ComponentSystemEvent event) {
		conference = conferenceService.get(id);
		talks = talkService.findByConference(conference);
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
	
}
