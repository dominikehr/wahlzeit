package org.wahlzeit.model;

public class Location {
	
	//associated with a particular coordinate
	public Coordinate coordinate;
	
	//constructor in which we instantiate coordinate (strong part-whole relationship)
	public Location(double x, double y, double z) {
		this.coordinate = new Coordinate(x,y,z);
	}
}
