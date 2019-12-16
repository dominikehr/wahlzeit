package org.wahlzeit.model;

import org.wahlzeit.customexceptions.CoordinateConversionException;

public interface Coordinate {	
	CartesianCoordinate asCartesianCoordinate() throws CoordinateConversionException;
	double getCartesianDistance(Coordinate coordinate);
	SphericCoordinate asSphericCoordinate() throws CoordinateConversionException;
	double getCentralAngle(Coordinate coordinate);
	boolean isEqual(Coordinate coordinate);
}
