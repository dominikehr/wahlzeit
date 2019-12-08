package org.wahlzeit.customexceptions;
/*
 * Custom checked exception wrapping exceptions that could possibly occur during (Football)Photo instantiation
 */
public class FootballPhotoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FootballPhotoException(String message) {
		super(message);
	}
	
	public FootballPhotoException(String message, Throwable cause) {
		super(message,cause);
	}

}
