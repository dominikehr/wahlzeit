package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

	/*
	 * convert instance on which we invoke this to CartesianCoordinate
	 * Subsequently invoke method on it which will be forwarded to CartesianCoordinate subclass
	 * where the full method implementation is given due to runtime polymorphism
	 */
	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		CartesianCoordinate cartCoord = this.asCartesianCoordinate();
		return cartCoord.getCartesianDistance(coordinate);
	}

	/*
	 * convert instance on which we invoke this to SphericCoordinate
	 * Subsequently invoke method on it which will be forwarded to SphericCoordinate subclass
	 * where the full method implementation is given due to runtime polymorphism
	 */
	@Override
	public double getCentralAngle(Coordinate coordinate) {
		SphericCoordinate sphereCoord = this.asSphericCoordinate();
		return sphereCoord.getCentralAngle(coordinate);
	}

	/*
	 * override equals method, Provide hashCode override in respective subclasses
	 * This forwards to isEqual which then uses runtime polymorphism to forward 
	 * to the particular type of Coordinate subclass
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if(obj == null) {
			return false;
		// equality can only be asserted for two instances of Coordinate
		} else if(!(obj instanceof Coordinate)) {
			return false;
		}
		
		Coordinate coord = (Coordinate) obj;
		// forward over to isEqual for further checks
		return isEqual(coord);
	}
	
	/*
	 * use of template design pattern: 
	 * isEqualHelper will be invoked on the Coordinate subclass involved using runtime polymorphism
	 */
	@Override
	public boolean isEqual(Coordinate coordinate) {
		return isEqualHelper(coordinate);
	}
	
	/*
	 * @methodtype: boolean query method
	 * @methodproperty: primitive
	 * using runtime polymorphism this will be invoked on the specific Coordinate subclass.
	 * Assesses equality in terms of coordinate attribute value correspondence
	 */
	protected abstract boolean isEqualHelper(Coordinate coordinate);
	
	/*
	 * @methodtype: comparison method
	 * provide double comparison method for all subclasses
	 */
	protected boolean compareDoubles(double firstDim, double secondDim) {
		//if either one of the numbers compared is NaN return false
		if(Double.isNaN(firstDim) || Double.isNaN(secondDim)) {
			return false;
		}
		//check if double values can be considered equal according to specified precision
		return Math.abs(firstDim - secondDim) < EPSm8; 
	}
	
	/*
	 * @methodtype: assertion method
	 * provide double assertion method for all subclasses
	 */
	protected void assertDoubleIsNotZero(double d) {
		if(d >= - EPSm8 && d <= EPSm8) {
			throw new IllegalArgumentException("Denominator of division is zero");
		}
	}

}
