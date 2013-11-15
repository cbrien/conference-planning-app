package com.prodyna.pac.conference.events.exceptions;

import javax.validation.ValidationException;

import com.prodyna.pac.conference.events.model.Talk;
import com.prodyna.pac.conference.users.model.User;

/**
 * The Class RoomOccupiedException.
 */
public class SpeakerOccupiedException extends ValidationException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The talk. */
	private final Talk talk;

	/** The speaker. */
	private final User speaker;
	
	/**
	 * Instantiates a new speaker occupied exception.
	 *
	 * @param talk the talk
	 */
	public SpeakerOccupiedException(Talk talk, User speaker) {
		super("speaker already occupied: " + speaker.getId());
		this.talk = talk;
		this.speaker = speaker;
	}

	/**
	 * @return the talk
	 */
	public Talk getTalk() {
		return talk;
	}

	/**
	 * @return the speaker
	 */
	public User getSpeaker() {
		return speaker;
	}
	

}
