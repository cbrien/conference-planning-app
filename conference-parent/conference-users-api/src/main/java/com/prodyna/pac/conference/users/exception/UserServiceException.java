package com.prodyna.pac.conference.users.exception;

public class UserServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public UserServiceException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
