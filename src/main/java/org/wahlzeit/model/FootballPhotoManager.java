package org.wahlzeit.model;

import java.util.logging.Logger;

import org.wahlzeit.utils.PatternInstance;

@PatternInstance (
		patternName = "Singleton",
		participants = { "Singleton (FootballPhotoManager)" }
)

public class FootballPhotoManager extends PhotoManager {

	
	public FootballPhotoManager() {
		photoTagCollector = FootballPhotoFactory.getInstance().createPhotoTagCollector();
	}

	//hide the final PhotoFactory instance of the superclass 
	protected static final FootballPhotoManager instance = new FootballPhotoManager();
	private static final Logger log = Logger.getLogger(FootballPhotoManager.class.getName());
	
	public static final FootballPhotoManager getInstance() {
		return instance;
	}
	
}
