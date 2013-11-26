package com.prodyna.pac.conference.common.exceptions;

public class ConferenceServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public ConferenceServiceException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public ConferenceServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
