package org.wahlzeit.customexceptions;


/*
 * Custom checked exception wrapping exceptions that could possibly occur during (Football)Photo instantiation
 */
public class FootballPhotoCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FootballPhotoCreationException(String message) {
		super(message);
	}
	
	public FootballPhotoCreationException(String message, Throwable cause) {
		super(message,cause);
	}

}
