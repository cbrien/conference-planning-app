package com.prodyna.pac.conference.facility.exception;

public class FacilityServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public FacilityServiceException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public FacilityServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
