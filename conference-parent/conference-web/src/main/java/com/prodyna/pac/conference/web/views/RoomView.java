package com.prodyna.pac.conference.web.views;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.facility.model.Room;
import com.prodyna.pac.conference.facility.service.RoomService;

@Model
public class RoomView {
	
	private Long id;
	
	private Room room;
	
	private List<Talk> talks;
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private TalkService talkService;

	public void init(ComponentSystemEvent event) {
		room = roomService.get(id);
		talks = talkService.findByRoom(room);
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
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @return the talks
	 */
	public List<Talk> getTalks() {
		return talks;
	}
	
}
