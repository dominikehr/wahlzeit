package org.wahlzeit.model;

public class Location {
	
	//associated with a particular coordinate
	public Coordinate coordinate;
	
	//constructor in which we instantiate coordinate (aggregation, so weak part-whole relationship)
	public Location(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	//setter for coordinate reference for runtime re-assignment
	public void setCoordinate (Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}
