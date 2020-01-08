package org.wahlzeit.model;

import java.util.ArrayList;
import java.util.List;

import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.utils.StringUtil;

import java.util.Date;


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
