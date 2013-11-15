package com.prodyna.pac.conference.events.exceptions;

import javax.validation.ValidationException;

import com.prodyna.pac.conference.events.model.Conference;

/**
 * The Class RoomOccupiedException.
 */
public class NotDuringConferenceException extends ValidationException{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The conference. */
	private final Conference conference;
	
	
	/**
	 * Instantiates a new NotDuringConferenceException.
	 *
	 * @param conference the conference
	 */
	public NotDuringConferenceException(Conference conference) {
		super("talk not during conference: "
				+ conference.getStartDate() + " to "
				+ conference.getEndDate());
		this.conference = conference;
	}


	/**
	 * @return the conference
	 */
	public Conference getConference() {
		return conference;
	}


}
