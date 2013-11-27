package com.prodyna.pac.conference.events.exceptions;

import javax.validation.ValidationException;

import com.prodyna.pac.conference.events.model.Conference;

/**
 * The Class RoomOccupiedException.
 */
public class LocationOccupiedException extends ValidationException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The conference. */
	private final Conference conference;
	
	/**
	 * Instantiates a new location occupied exception.
	 *
	 * @param conference the talk
	 */
	public LocationOccupiedException(Conference conference) {
		super("location already occupied by conference: " + conference.getId());
		this.conference = conference;
	}

	/**
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}
	

}
