package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate {
	//latitude
	private double phi;
	//longitude
	private double theta;
	private double radius;
	
	public SphericCoordinate(double phi, double theta, double radius) {
		assertIsValidPhi(phi);
		this.phi = phi;
		
		assertIsValidTheta(theta);
		this.theta = theta;
		
		assertIsValidRadius(radius);
		this.radius = radius;
	}
	
	public double getPhi() {
		return phi;
	}

	public void setPhi(double phi) {
		assertIsValidPhi(phi);
		this.phi = phi;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		assertIsValidTheta(theta);
		this.theta = theta;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		assertIsValidRadius(radius);
		this.radius = radius;
	}
	
	
	/*
	 * @methodtype: assertion 
	 */
	private void assertIsValidPhi(double phi) {
		if(Double.isNaN(phi) || Double.isInfinite(phi) || phi < 0.0 || phi >= 2*Math.PI) {
			throw new IllegalArgumentException("Phi must be a valid number between zero and 2PI!");
		}
	}

	/*
	 * @methodtype: assertion 
	 */
	private void assertIsValidTheta(double theta) {
		if(Double.isNaN(theta) || Double.isInfinite(theta) || theta < 0.0 || theta > Math.PI) {
			throw new IllegalArgumentException("Theta must be a valid number between zero and PI!");
		}
	}
	
	/*
	 * @methodtype: assertion 
	 */
	private void assertIsValidRadius(double radius) {
		if(Double.isNaN(radius) || Double.isInfinite(radius) || radius < 0.0) {
			throw new IllegalArgumentException("Radius must be a valid non-negative number!");
		}
	}

	/*
	 * @methodtype: conversion
	 * @methodproperty: composed
	 */
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = sphericCoordinateAsCartesianX();
		double y = sphericCoordinateAsCartesianY();
		double z = sphericCoordinateAsCartesianZ();
		
		return new CartesianCoordinate(x,y,z);
	}
	
	/*
	 * @methodtype: conversion
	 * @methodproperty: primitive
	 */
	private double sphericCoordinateAsCartesianX() {
		return this.getRadius() * Math.sin(this.getPhi()) * Math.cos(this.getTheta());
	}


	/*
	 * @methodtype: conversion
	 * @methodproperty: primitive
	 */
	private double sphericCoordinateAsCartesianY() {
		return this.getRadius() * Math.sin(this.getPhi()) * Math.sin(this.getTheta());
	} 
	
	/*
	 * @methodtype: conversion
	 * @methodproperty: primitive
	 */
	private double sphericCoordinateAsCartesianZ() {
		return this.getRadius() * Math.cos(this.getPhi());
	} 
		
	/*
	 * @methodtype: conversion method
	 */
	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}
	
	@Override
	public double getCentralAngle(Coordinate coordinate) {
		SphericCoordinate sphereCoord = coordinate.asSphericCoordinate();
		return doGetCentralAngle(sphereCoord);
	}
	
	/*
	 * @methodtype: get method
	 * @methodproperty: primitive
	 * computation as per spherical law of cosine: https://en.wikipedia.org/wiki/Great-circle_distance#Formulae
	 */
	private double doGetCentralAngle(SphericCoordinate sphereCoord) {
		// delta longitude (delta theta)
		double longitudeDiff = Math.abs(this.getTheta() - sphereCoord.getTheta());
		return Math.acos(Math.sin(this.getPhi()) * Math.sin(sphereCoord.getPhi()) + 
				Math.cos(this.getPhi()) * Math.cos(sphereCoord.getPhi()) * Math.cos(longitudeDiff));
	}
	
	/*
	 * @methodtype: boolean query method
	 * @methodproperty: primitive
	 * custom-built equality contract determining equality 
	 * based on equal coordinate variables
	 */
	@Override
	protected boolean isEqualHelper(Coordinate coordinate) {
		// as this method has been invoked on a Coordinate of dynamic type SphericCoordinate
		// we convert the coordinate parameter into SphericCoordinate as well and invoke
		// equality check in terms of double values on two SphericCoordinates
		SphericCoordinate sphereCoord = coordinate.asSphericCoordinate();
		
		//check equality of double values using helper method provided in abstract superclass
		boolean eqPhi = compareDoubles(this.getPhi() , sphereCoord.getPhi());
		boolean eqTheta = compareDoubles(this.getTheta(), sphereCoord.getTheta());
		boolean eqRadius = compareDoubles(this.getRadius(), sphereCoord.getRadius());
		//if all three equal then equality between two coordinates is given
		return eqPhi && eqTheta && eqRadius;
	}

	// override hashCode as customary when overriding equals()
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(phi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(theta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
		
}
