package org.wahlzeit.model;

import java.util.Date;

import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;


/**
 * 
 * Instantiation process of a FootballPhoto:
 * 
 * The instantiation process entails several consecutive steps that begin inside ModelMain.
 * Here, inside the createUser method the createPhoto method is invoked on a singleton instance of the PhotoManager class.
 * The createPhoto method inside PhotoManager uses the static createPhoto method inside PhotoUtil.
 * Inside PhotoUtil I have replaced the previous generic PhotoFactory with my own domain extension i.e. FootballPhotoFactory.
 * The createPhoto method inside PhotoUtil retrieves a singleton instance of said FootballPhotoFactory on which it invokes
 * the createPhoto method, taking a PhotoId instance as a parameter.
 * Inside the factory createPhoto method in FootballPhotoFactory instantiation actually takes place by invoking the 
 * FootballPhoto constructor. For this I provide several overloaded constructors delegating between oneanother in order
 * to initialize several attributes--like the Football type object associated with the FootballPhoto--to their (default) values.
 *
 * This detailed description translates to the following key facts when determining its position as a sixtuple inside 
 * the solution design space:
 * 
 * 1. Delegation of Object Creation: separate-object
 * 2. Selection of Concrete Class: By-subclassing 
 * 3. Configuration of Class Mapping: In-code
 * 4. Instantiation of Concrete Class: In-code
 * 5. Initialization of New Object: Default ("In-second-step" option equally applicable as provided setter methods allow for this)
 * 6. Building of Object Structure: Default
 *
 */


public class FootballPhoto extends Photo {
	/*
	 *  The particular fooball scene depicted on the associated photo. Refrained from making this final
	 *  as FootballPhoto still workable without this attribute set
	 */
	private Football football;
	/*
	 *  Date on which the FootballPhoto was taken. Refrained from making this final
	 *  as FootballPhoto still workable without this attribute set
	 */
	private Date timeTaken;
	private static final FootballManager footballManager = FootballManager.getInstance();
	// Default Football scene in order to initialize a default football photo if none provided
	private static final Football FOOTBALL_DEFAULT = footballManager.createFootball(FootballTypeScene.DEFAULT, Tournament.DEFAULT);
	/*
	 * @methodtype constructor
	 * Various (no-)argument constructors for initialization
	 */
	public FootballPhoto() {
		super();
	}
	
	// overloaded constructor that instantiates FootballPhoto with default scene
	public FootballPhoto(PhotoId id) {
		this(id, FOOTBALL_DEFAULT);
	}
	
	public FootballPhoto(PhotoId id, Football football) {
		this(id, football, new Date(0));
	}
	
	public FootballPhoto(PhotoId id, Football football, Date date) {
		super(id);
		if(id == null) throw new IllegalArgumentException("PhotoID must not be null");
		if(football == null) throw new IllegalArgumentException("Football must not be null");
		if(date == null) throw new IllegalArgumentException("Date must not be null");
		this.id = id;
		this.football = football;
		this.setTimeTaken(date);
	}

	/*
	 * @methodtype get
	 * returns Football instance associated with this photo
	 */
	public Football getFootball() {
		return football;
	}

	/*
	 * @methodtype set
	 * allows setting a new association between FootballPhoto instance and Football instance
	 */
	public void setFootball(Football football) {
		this.football = football;
	}
	
	/*
	 * @methodtype get
	 */
	public Date getTimeTaken() {
		return timeTaken;
	}

	/*
	 * @methodtype set
	 */
	public void setTimeTaken(Date timeTaken) {
		this.timeTaken = timeTaken;
	}
	
	

}
