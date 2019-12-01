package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

	/*
	 * convert instance on which we invoke this to CartesianCoordinate
	 * Subsequently invoke method on it which will be forwarded to CartesianCoordinate subclass
	 * where the full method implementation is given due to runtime polymorphism
	 */
	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		assertClassInvariants();
		CartesianCoordinate cartCoord = this.asCartesianCoordinate();
		return cartCoord.getCartesianDistance(coordinate);
	}

	/*
	 * @methodtype: assertion method
	 * using runtime polymorphism this will be invoked on the specific Coordinate subclass.
	 * asserts that class invariants are always maintained before and after method invocation
	 */
	protected abstract void assertClassInvariants();

	/*
	 * convert instance on which we invoke this to SphericCoordinate
	 * Subsequently invoke method on it which will be forwarded to SphericCoordinate subclass
	 * where the full method implementation is given due to runtime polymorphism
	 */
	@Override
	public double getCentralAngle(Coordinate coordinate) {
		assertClassInvariants();
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
	
}
