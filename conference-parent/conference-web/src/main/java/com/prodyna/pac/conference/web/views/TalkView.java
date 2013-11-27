package com.prodyna.pac.conference.web.views;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.users.model.User;

/**
 * The Class TalkView represents the request data for the talk view.
 */
@Model
public class TalkView {
	
	private Long id;
	
	private Talk talk;
	
	private List<User> speakers;
	
	@Inject
	private TalkService talkService;

	public void init(ComponentSystemEvent event) {
		talk = talkService.get(id);
		speakers = new ArrayList<User>(talk.getSpeakers());
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
	public Talk getTalk() {
		return talk;
	}
	
	/**
	 * Gets the speakers.
	 *
	 * @return the speakers
	 */
	public List<User> getSpeakers() {
		return speakers;
	}

}
