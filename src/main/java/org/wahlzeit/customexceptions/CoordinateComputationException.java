package org.wahlzeit.customexceptions;

import org.wahlzeit.model.CartesianCoordinate;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.SphericCoordinate;

public class CoordinateComputationException extends RuntimeException {
	
	public CoordinateComputationException(Coordinate coord ,String message) {
		super(getMessage(coord) + " " + message);
	}
	
	private static String getMessage(Coordinate coord) {
		String msg = null;
		if(coord instanceof CartesianCoordinate) {
			CartesianCoordinate cartCoord = (CartesianCoordinate) coord;
			msg = "Operation on cartesian coordinate with values x: " + cartCoord.getX() +
					" value y: " + cartCoord.getY() +
					" value z " + cartCoord.getZ() + ".";
		} else {
			SphericCoordinate sphereCoord = (SphericCoordinate)coord;
			msg = "Operation on spheric coordinate with value phi: " + sphereCoord.getPhi() +
					" value theta: " + sphereCoord.getTheta() +
					" value radius " + sphereCoord.getRadius() + ".";
		}
		return msg;
	}
}
