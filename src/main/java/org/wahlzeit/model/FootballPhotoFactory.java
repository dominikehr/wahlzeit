package org.wahlzeit.model;

import java.util.logging.Logger;
import java.util.Date;

import org.wahlzeit.customexceptions.FootballPhotoCreationException;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.PatternInstance;


@PatternInstance (
		patternName = "Singleton",
		participants = { "Singleton (FootballPhotoFactory)" }
)

public class FootballPhotoFactory extends PhotoFactory {

	//provide Logger for custom PhotoFactory
	private static final Logger log = Logger.getLogger(FootballPhotoFactory.class.getName());
	//singleton instance hiding the superclass instance
	private static FootballPhotoFactory instance = null;
	
	//make constructor only accessible to same-package subclasses, instantiation via getInstance
	protected FootballPhotoFactory() {
		// do nothing
	}
	
	/*
	 * Hide initialize method from superclass in order to invoke our getInstance()
	 */
	public static void initialize() {
		//invokes hidden getInstance inside this class
		FootballPhotoFactory.getInstance();
	}
	
	/*
	 * @methodtype get
	 * hide getInstance() method of PhotoFactory superclass.
	 * Retrieves instance of our custom FootballPhotoFactory
	 * 
	 */
	public static synchronized FootballPhotoFactory getInstance() {
		if(instance == null) {
			log.config(LogBuilder.createSystemMessage().addAction("setting generic FootballPhotoFactory").toString());
			//call hidden setInstance method we provide in this class
			setInstance(new FootballPhotoFactory());
		}
		return instance;
	}
	
	/*
	 * @methodtype set
	 * hide setInstance() method of PhotoFactory superclass.
	 * sets singleton instance to instance of our custom FootballPhotoFactory
	 * 
	 */
	public static synchronized void setInstance(FootballPhotoFactory fpFactory) {
		if(instance != null) {
			throw new IllegalStateException("attempt to initialize FootballPhotoFactory twice");
		}
		instance = fpFactory;
	}
	
	
	/*
	 * @methodtype factory
	 * invoke constructor of our custom FootballPhoto class overriding PhotoFactory superclass method
	 */
	@Override
	public Photo createPhoto() {
		return new FootballPhoto();
	}
	
	/*
	 * @methodtype: factory
	 * Create new FootballPhoto with specific PhotoId. Override from superclass
	 */
	@Override
	public Photo createPhoto(PhotoId id) throws FootballPhotoCreationException {
		Photo footballPhoto;
		try {
			footballPhoto = new FootballPhoto(id);
		} catch (IllegalArgumentException e) {
			throw new FootballPhotoCreationException("Instantation of Football photo failed, see details: " + e.getMessage());
		}
		return footballPhoto;
	}
	
	/*
	 * @methodtype: factory
	 * Overloaded factory method that also takes in Football as parameter
	 */
	public Photo createPhoto(PhotoId id, Football football) throws FootballPhotoCreationException {
		Photo footballPhoto;
		try {
			footballPhoto = new FootballPhoto(id, football);
		} catch (IllegalArgumentException e) {
			throw new FootballPhotoCreationException("Instantation of Football photo failed, see details: " + e.getMessage());
		}
		return footballPhoto;
	}
	
	/*
	 * @methodtype: factory
	 * Overloaded factory method that also takes in Date as parameter
	 */
	public Photo createPhoto(PhotoId id, Football football, Date date) throws FootballPhotoCreationException {
		Photo footballPhoto;
		try {
			footballPhoto = new FootballPhoto(id, football, date);
		} catch (IllegalArgumentException e) {
			throw new FootballPhotoCreationException("Instantation of Football photo failed, see details: " + e.getMessage());
		}
		return footballPhoto;
	}


}
