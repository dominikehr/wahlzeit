package org.wahlzeit.model;

import org.wahlzeit.utils.DoubleValuesUtil;

public class CartesianCoordinate extends AbstractCoordinate{
	// three private coordinate Variables reflecting x,y,z dimensions
		private double x;
		private double y;
		private double z;
		
		public CartesianCoordinate(double x, double y, double z) {
			boolean isValidDouble = DoubleValuesUtil.isValidDouble(x) &&
					DoubleValuesUtil.isValidDouble(y) && DoubleValuesUtil.isValidDouble(z);
			if(!isValidDouble) {
				throw new IllegalArgumentException("Dimensional attributes x,y,z must all be valid and finite double values");	
			}
			
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		/*
		 * @methodtype get methods and set methods
		 */
		public double getX() {
			return x;
		}

		public void setX(double x) {
			if(!DoubleValuesUtil.isValidDouble(x)) {
				throw new IllegalArgumentException("Dimensional attribute x must be a valid and finite double value");
			}
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			if(!DoubleValuesUtil.isValidDouble(y)) {
				throw new IllegalArgumentException("Dimensional attribute y must be a valid and finite double value");
			}
			this.y = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			if(!DoubleValuesUtil.isValidDouble(z)) {
				throw new IllegalArgumentException("Dimensional attribute z must be a valid and finite double value");
			}
			this.z = z;
		}
		
		/*
		 * @methodtype: conversion method
		 */
		@Override
		public CartesianCoordinate asCartesianCoordinate() {
			return this;
		}
		
		//returns the Cartesian distance between two points
		@Override
		public double getCartesianDistance(Coordinate coordinate) {			
			//use conversion method to get CartesianCoordiante 
			CartesianCoordinate cartCoord = coordinate.asCartesianCoordinate();
			double cartDistance = Math.sqrt(
					Math.pow(this.x - cartCoord.getX(), 2)
					+ Math.pow(this.y - cartCoord.getY(), 2) 
					+ Math.pow(this.z - cartCoord.getZ(), 2)
							);
			// ensure that computation did not violate class invariants 
			assertClassInvariants();
			return cartDistance;
		}
		
		/*
		 * @methodtype: conversion method
		 * @methodproperty: composed
		 */
		@Override
		public SphericCoordinate asSphericCoordinate() {
			//first make sure that we start the conversion with a valid CartesianCoordinate
			assertClassInvariants();
			
			double phi = cartesianCoordinateAsSphericPhi();
			double theta = cartesianCoordinateAsSphericTheta();
			double radius = cartesianCoordinateAsSphericRadius();
			
			// ensure that computation did not violate class invariants 
			assertClassInvariants();
			return new SphericCoordinate(phi, theta, radius);
		}
		
		/*
		 * @methodtype: conversion method
		 * @methodproperty: primitive
		 */
		private double cartesianCoordinateAsSphericRadius() {
			return Math.sqrt(Math.pow(this.getX(), 2) +
					Math.pow(this.getY(), 2) + 
					Math.pow(this.getZ(), 2)
							);
		}
		
		/*
		 * @methodtype: conversion method
		 * @methodproperty: primitive
		 */
		private double cartesianCoordinateAsSphericTheta() {
			// verify precondition before performing calculation
			DoubleValuesUtil.assertDoubleIsNotZero(this.getX());
			// use atan2 instead of atan to deal with corner cases
			return Math.atan2(this.getY(), this.getX());
		}
		
		/*
		 * @methodtype: conversion method
		 * @methodproperty: primitive
		 */
		private double cartesianCoordinateAsSphericPhi() {
			// verify precondition before performing calculation
			DoubleValuesUtil.assertDoubleIsNotZero(this.cartesianCoordinateAsSphericRadius());
			return Math.acos(this.getZ() / this.cartesianCoordinateAsSphericRadius());
		}
		
		/*
		 * @methodtype: boolean query method
		 * @methodproperty: primitive
		 * custom-built equality contract determining equality 
		 * based on equal coordinate variables
		 */
		@Override
		protected boolean isEqualHelper(Coordinate coordinate) {
			// as this method has been invoked on a Coordinate of dynamic type CartesianCoordinate
			// we convert the coordinate parameter into CartesianCoordinate as well and invoke
			// equality check in terms of double values on two CartesianCoordinates
			CartesianCoordinate cartCoord = coordinate.asCartesianCoordinate();
			
			//check equality of double values using helper method provided in abstract superclass
			boolean eqX = DoubleValuesUtil.compareDoubles(this.getX() , cartCoord.getX());
			boolean eqY = DoubleValuesUtil.compareDoubles(this.getY(), cartCoord.getY());
			boolean eqZ = DoubleValuesUtil.compareDoubles(this.getZ(), cartCoord.getZ());
			//if all three equal then equality between two coordinates is given
			return eqX && eqY && eqZ;
		}
		
		// override hashCode as customary when overriding equals()
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(x);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(y);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(z);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}
		
		/*
		 * @methodtype: assertion method
		 * ensuring that class invariants are always maintained
		 */
		@Override
		protected void assertClassInvariants() {
			assert !Double.isNaN(x);
			assert !Double.isNaN(y);
			assert !Double.isNaN(z);
			assert Double.isFinite(x);
			assert Double.isFinite(y);
			assert Double.isFinite(z);
			
		}



}
