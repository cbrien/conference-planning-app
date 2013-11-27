package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.users.model.User;
import com.prodyna.pac.conference.users.service.UserService;

@Model
public class SpeakerView {
	
	private Long id;
	
	private User speaker;
	
	private List<Talk> talks;
	
	@Inject
	private UserService userService;
	
	@Inject
	private TalkService talkService;

	public void init(ComponentSystemEvent event) {
		speaker = userService.get(id);
		talks = talkService.findBySpeaker(speaker);
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
	public User getSpeaker() {
		return speaker;
	}

	/**
	 * @return the talks
	 */
	public List<Talk> getTalks() {
		return talks;
	}

	/**
	 * @param speaker the speaker to set
	 */
	public void setSpeaker(User speaker) {
		this.speaker = speaker;
	}
	
}
