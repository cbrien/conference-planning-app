package com.prodyna.pac.conference.web.views;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

import org.richfaces.cdi.push.Push;

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.events.service.TalkService;
import com.prodyna.pac.conference.users.model.User;

/**
 * The Class TalkView represents the request data for the talk view.
 */
@Model
public class TalkView {

	public static final String PUSH_CDI_TOPIC = "pushCdi";

	@Inject
	@Push(topic = PUSH_CDI_TOPIC)
	Event<String> pushEvent;

	@Inject
	FacesContext facesContext;
	
	@Inject
	List<User> userList;

	private Long id;

	private Talk talk = new Talk();

	private List<User> availableSpeakers = new ArrayList<User>();

	@Inject
	private TalkService talkService;

	public void init(ComponentSystemEvent event) {
		talk = talkService.get(id);
		loadAvailableSpeakers();
	}

	private void loadAvailableSpeakers() {
		availableSpeakers.clear();
		for (User user : userList) {
			if (!talk.getSpeakers().contains(user)) {
				availableSpeakers.add(user);
			}
		}
	}

	public void save() {
		try {
			if (talk.getId() == null || talk.getId().longValue() == 0) {
				talk.setId(null);
				talkService.add(talk);
				pushEvent.fire(String.format("New talk added: %s (id: %d)",
						talk.getName(), talk.getId()));
			} else {
				talkService.update(talk);
				pushEvent.fire(String.format("Talk saved: %s (id: %d)",
						talk.getName(), talk.getId()));
			}
		} catch (Exception e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Unable to save talk: " + e.getMessage(),
							"Unable to save talk: " + e.getMessage()));
		}
	}

	public void delete() {
		try {
			Long talkId = talk.getId();
			if (talkId != null && talkId.longValue() != 0) {
				talkService.delete(talkId);
				pushEvent.fire(String.format("Talk deleted: %s (id: %d)",
						talk.getName(), talkId));
			}
		} catch (Exception e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Unable to save talk: " + e.getMessage(),
							"Unable to save talk: " + e.getMessage()));
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
	 * @return the user
	 */
	public Talk getTalk() {
		return talk;
	}


	/**
	 * @param talk
	 *            the talk to set
	 */
	public void setTalk(Talk talk) {
		this.talk = talk;
		loadAvailableSpeakers();
	}

	/**
	 * @return the availableSpeakers
	 */
	public List<User> getAvailableSpeakers() {
		return availableSpeakers;
	}

	/**
	 * @param availableSpeakers the availableSpeakers to set
	 */
	public void setAvailableSpeakers(List<User> availableSpeakers) {
		this.availableSpeakers = availableSpeakers;
	}

}
