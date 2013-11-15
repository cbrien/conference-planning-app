package com.prodyna.pac.conference.events.exceptions;

import javax.validation.ValidationException;

import com.prodyna.pac.conference.events.model.Talk;

/**
 * The Class RoomOccupiedException.
 */
public class RoomOccupiedException extends ValidationException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The talk. */
	private final Talk talk;
	
	/**
	 * Instantiates a new room occupied exception.
	 *
	 * @param talk the talk
	 */
	public RoomOccupiedException(Talk talk) {
		super("room already occupied by talk: " + talk.getId());
		this.talk = talk;
	}

	/**
	 * @return the talk
	 */
	public Talk getTalk() {
		return talk;
	}
	

}
