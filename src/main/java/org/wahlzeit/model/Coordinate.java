package org.wahlzeit.model;

public interface Coordinate {
	//epsilon value to compare doubles
	double EPSm8 = 1e-8;
	
	CartesianCoordinate asCartesianCoordinate();
	double getCartesianDistance(Coordinate coordinate);
	SphericCoordinate asSphericCoordinate();
	double getCentralAngle(Coordinate coordinate);
	boolean isEqual(Coordinate coordinate);
}
