package org.wahlzeit.model;

public class CartesianCoordinate implements Coordinate{
	//three private coordinate Variables reflecting x,y,z dimensions
		private double x;
		private double y;
		private double z;
		
		public CartesianCoordinate(double x, double y, double z) {
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
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
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
			return Math.sqrt(
					Math.pow(this.x - cartCoord.getX(), 2)
					+ Math.pow(this.y - cartCoord.getY(), 2) 
					+ Math.pow(this.z - cartCoord.getZ(), 2)
							);
		}
		
		//overwrite equals() method and forward to isEqual()
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CartesianCoordinate coord = (CartesianCoordinate) obj;
			return isEqual(coord);
		}
		
		/*
		 * @methodtype boolean query method
		 * custom-built equality contract determining equality 
		 * based on equal coordinate variables
		 */
		@Override
		public boolean isEqual(Coordinate coordinate) {
			//check if null in case direct call to this method
			if(coordinate == null) {
				return false;
			}
			
			//use conversion method to get CartesianCoordiante 
			CartesianCoordinate cartCoord = coordinate.asCartesianCoordinate();
			
			boolean eqX = compareDoubles(this.getX() , cartCoord.getX());
			boolean eqY = compareDoubles(this.getY(), cartCoord.getY());
			boolean eqZ = compareDoubles(this.getZ(), cartCoord.getZ());
			//if all three equal then equality between two coordinates is given
			return eqX && eqY && eqZ;
		}
		
		/*
		 * @methodtype: comparison method
		 */
		private boolean compareDoubles(double firstDim, double secondDim) {
			//if either one of the numbers compared is NaN return false
			if(Double.isNaN(firstDim) || Double.isNaN(secondDim)) {
				return false;
			}
			//check if double values can be considered equal according to specified precision
			return Math.abs(firstDim - secondDim) < EPSm8; 
		}
		
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
		 * @methodtype: conversion method
		 * @methodproperty: composed
		 */
		@Override
		public SphericCoordinate asSphericCoordinate() {
			double phi = cartesianCoordinateAsSphericPhi();
			double theta = cartesianCoordinateAsSphericTheta();
			double radius = cartesianCoordinateAsSphericRadius();
			
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
			assertDoubleIsNotZero(this.getX());
			return Math.atan(this.getY() / this.getX());
		}
		
		/*
		 * @methodtype: conversion method
		 * @methodproperty: primitive
		 */
		private double cartesianCoordinateAsSphericPhi() {
			assertDoubleIsNotZero(this.cartesianCoordinateAsSphericRadius());
			return Math.acos(this.getZ() / this.cartesianCoordinateAsSphericRadius());
		}
			
		/*
		 * @methodtype: assertion method
		 */
		private void assertDoubleIsNotZero(double d) {
			if(d >= - EPSm8 && d <= EPSm8) {
				throw new IllegalArgumentException("Denominator of division is zero");
			}
		}
		
		@Override
		public double getCentralAngle(Coordinate coordinate) {
			//forward to computation of CentralAngle inside SphericCoordinate class
			return this.asSphericCoordinate().getCentralAngle(coordinate);
		}

}
