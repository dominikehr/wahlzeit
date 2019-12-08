package org.wahlzeit.model;

import org.wahlzeit.utils.DoubleValuesUtil;

public class SphericCoordinate extends AbstractCoordinate {
	//latitude
	private double phi;
	//longitude
	private double theta;
	private double radius;
	
	public SphericCoordinate(double phi, double theta, double radius) {
		boolean isValidDouble = DoubleValuesUtil.isValidDouble(phi) &&
				DoubleValuesUtil.isValidDouble(theta) && DoubleValuesUtil.isValidDouble(radius);
		if(!isValidDouble) {
			throw new IllegalArgumentException("Attributes phi, theta, radius must all be valid and finite double values");
		}
		
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
		// check whether valid input provided else throw exception
		assertIsValidPhi(phi);
		this.phi = phi;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		// check whether valid input provided else throw exception
		assertIsValidTheta(theta);
		this.theta = theta;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		// check whether valid input provided else throw exception
		assertIsValidRadius(radius);
		this.radius = radius;
	}
	
	/*
	 * @methodtype: assertion 
	 */
	protected static void assertIsValidPhi(double phi) {
		if(!DoubleValuesUtil.isValidDouble(phi) || phi < 0.0 || phi >= 2*Math.PI) {
			throw new IllegalArgumentException("Phi must be a valid number between zero and 2PI!");
		}
	}

	/*
	 * @methodtype: assertion 
	 */
	protected static void assertIsValidTheta(double theta) {
		if(!DoubleValuesUtil.isValidDouble(theta) || theta < 0.0 || theta > Math.PI) {
			throw new IllegalArgumentException("Theta must be a valid number between zero and PI!");
		}
	}
	
	/*
	 * @methodtype: assertion 
	 */
	protected static void assertIsValidRadius(double radius) {
		if(!DoubleValuesUtil.isValidDouble(radius) || radius < 0.0) {
			throw new IllegalArgumentException("Radius must be a valid non-negative number!");
		}
	}

	/*
	 * @methodtype: conversion
	 * @methodproperty: composed
	 */
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		//first make sure that we start the conversion with a valid SphericCoordinate
		assertClassInvariants();
		
		double x = sphericCoordinateAsCartesianX();
		double y = sphericCoordinateAsCartesianY();
		double z = sphericCoordinateAsCartesianZ();
		
		// ensure that computation did not violate class invariants 
		assertClassInvariants();
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
		
	/*
	 * @methodtype: get method
	 * @methodproperty: primitive
	 * computation as per spherical law of cosine: https://en.wikipedia.org/wiki/Great-circle_distance#Formulae
	 */
	protected double doGetCentralAngle(SphericCoordinate sphereCoord) {
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
		boolean eqPhi = DoubleValuesUtil.compareDoubles(this.getPhi() , sphereCoord.getPhi());
		boolean eqTheta = DoubleValuesUtil.compareDoubles(this.getTheta(), sphereCoord.getTheta());
		boolean eqRadius = DoubleValuesUtil.compareDoubles(this.getRadius(), sphereCoord.getRadius());
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

	/*
	 * @methodtype: assertion method
	 * ensuring that class invariants are always maintained
	 */
	@Override
	protected void assertClassInvariants() {
		if(Double.isNaN(phi)) throw new IllegalStateException("attribute phi is NaN");
		if(Double.isNaN(theta)) throw new IllegalStateException("attribute theta is NaN");
		if(Double.isNaN(radius)) throw new IllegalStateException("attribute radius is NaN");
		if(Double.isInfinite(phi)) throw new IllegalStateException("attribute phi is infinite");
		if(Double.isInfinite(theta)) throw new IllegalStateException("attribute theta is infinite");
		if(Double.isInfinite(radius)) throw new IllegalStateException("attribute radius is infinite");
		
		
		// attribute-specific checks
		if(phi < 0.0 || phi >= 2* Math.PI) throw new IllegalStateException("Phi must be >= 0 and < 2PI");
		if(theta < 0.0 || theta > Math.PI) throw new IllegalStateException("Theta must be >= 0 and <= PI");
		if(radius < 0.0) throw new IllegalStateException("radius must be >= 0.0");
		
	}
		
}
