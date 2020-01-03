package org.wahlzeit.model;

import java.util.HashMap;
import java.util.Map;

import org.wahlzeit.customexceptions.CoordinateConversionException;
import org.wahlzeit.utils.DoubleValuesUtil;
import org.wahlzeit.utils.PatternInstance;

@PatternInstance ( 
		patternName = "Template Method",
		participants = { "AbstractClass (AbstractCoordinate)",
				"ConcreteClass (CartesianCoordinate, SphericCoordinate)" 
						} 
) 

public class SphericCoordinate extends AbstractCoordinate {
	//latitude
	private final double phi;
	//longitude
	private final double theta;
	private final double radius;
	
	private static final Map<Integer, SphericCoordinate> sphereCoordMap = new HashMap<>();
	
	
	private SphericCoordinate(double phi, double theta, double radius) {
		this.phi = phi;
		this.theta = theta;
		this.radius = radius;
	}
	
	public static SphericCoordinate getInstance(double phi, double theta, double radius) {
		boolean isValidDouble = DoubleValuesUtil.isValidDouble(phi) &&
				DoubleValuesUtil.isValidDouble(theta) && DoubleValuesUtil.isValidDouble(radius);
		if(!isValidDouble) {
			throw new IllegalArgumentException("Attributes phi, theta, radius must all be valid and finite double values");
		}
		assertIsValidPhi(phi);
		assertIsValidTheta(theta);
		assertIsValidRadius(radius);
		
		// retrieve key by using helper method. No beforehand SphericCoordinate instantiation needed
		Integer key = doGetHashCode(phi,theta,radius);
		// Look-up
		SphericCoordinate result = sphereCoordMap.get(key);
		if(result == null) {
			synchronized(SphericCoordinate.class) {
				result = sphereCoordMap.get(key);
				if(result == null) {
					result = new SphericCoordinate(phi,theta,radius);
					sphereCoordMap.put(key, result);
				}
			}
		}
		return result;
	}
	
	public double getPhi() {
		return phi;
	}
	
	/*
	 *  Setter within value object pattern that instantiates new SphericCoordinate in case of valid given phi
	 *  If upon setting value we have two equal instances return one of them
	 */
	public SphericCoordinate newSphericCoordinateWithPhi(double phi) {
		// check whether valid input provided else throw exception
		assertIsValidPhi(phi);
		return SphericCoordinate.getInstance(phi, this.getTheta(), this.getRadius());
	}

	public double getTheta() {
		return theta;
	}

	/*
	 *  Setter within value object pattern that instantiates new SphericCoordinate in case of valid given theta
	 *  If upon setting value we have two equal instances return one of them
	 */
	public SphericCoordinate newSphericCoordinateWithTheta(double theta) {
		// check whether valid input provided else throw exception
		assertIsValidTheta(theta);
		return SphericCoordinate.getInstance(this.getPhi(), theta, this.getRadius());
	}

	public double getRadius() {
		return radius;
	}

	/*
	 *  Setter within value object pattern that instantiates new SphericCoordinate in case of valid given radius
	 *  If upon setting value we have two equal instances return one of them
	 */
	public SphericCoordinate newSphericCoordinateWithRadius(double radius) {
		// check whether valid input provided else throw exception
		assertIsValidRadius(radius);
		return SphericCoordinate.getInstance(this.getPhi(),this.getTheta(), radius);
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
	public CartesianCoordinate asCartesianCoordinate() throws CoordinateConversionException {
		//first make sure that we start the conversion with a valid SphericCoordinate
		assertClassInvariants();
		
		double x;
		double y;
		double z;
		try {
			x = sphericCoordinateAsCartesianX();
			y = sphericCoordinateAsCartesianY();
			z = sphericCoordinateAsCartesianZ();
		} catch (IllegalArgumentException e) {
			throw new CoordinateConversionException(this, "Conversion into cartesian failed. Cause: " + e.getMessage());
		}
		
		// ensure that computation did not violate class invariants 
		assertClassInvariants();
		return CartesianCoordinate.getInstance(x, y, z);
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
	 * based on equal coordinate variables. Used within template method.
	 */
	@Override
	protected boolean isEqualHelper(Coordinate coordinate) {
		// as this method has been invoked on a Coordinate of dynamic type SphericCoordinate
		// we convert the coordinate parameter into SphericCoordinate as well and invoke
		// equality check in terms of double values on two SphericCoordinates
		SphericCoordinate sphereCoord;
		try {
			sphereCoord = coordinate.asSphericCoordinate();
		} catch (CoordinateConversionException e) {
			return false;
		}
		
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
		// delegate to doGetHashCode helper method
		int hashCode = doGetHashCode(this.getPhi(), this.getTheta(), this.getRadius());
		return hashCode;
	}

	/*
	 * @methodtype: get method
	 * @methodproperty: primitive
	 * Features computation of hashCode value. Static in order to make hashCode computation
	 * unreliant on already instantiated objects for value object look-up
	 */
	private static int doGetHashCode(double v1, double v2, double v3) {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(v1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(v2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(v3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/*
	 * @methodtype: assertion method
	 * ensuring that class invariants are always maintained
	 */
	@Override
	protected void assertClassInvariants() {
		String msg = "Class invariants violated: ";
		if(Double.isNaN(phi)) throw new IllegalStateException(msg + "attribute phi is NaN");
		if(Double.isNaN(theta)) throw new IllegalStateException(msg + "attribute theta is NaN");
		if(Double.isNaN(radius)) throw new IllegalStateException(msg + "attribute radius is NaN");
		if(Double.isInfinite(phi)) throw new IllegalStateException(msg + "attribute phi is infinite");
		if(Double.isInfinite(theta)) throw new IllegalStateException(msg + "attribute theta is infinite");
		if(Double.isInfinite(radius)) throw new IllegalStateException(msg + "attribute radius is infinite");
		
		
		// attribute-specific checks
		if(phi < 0.0 || phi >= 2* Math.PI) throw new IllegalStateException(msg + "Phi must be >= 0 and < 2PI");
		if(theta < 0.0 || theta > Math.PI) throw new IllegalStateException(msg + "Theta must be >= 0 and <= PI");
		if(radius < 0.0) throw new IllegalStateException(msg + "radius must be >= 0.0");
		
	}
		
}
