package org.wahlzeit.model;

import org.wahlzeit.utils.DoubleValuesUtil;

public abstract class AbstractCoordinate implements Coordinate {
	
	/*
	 * convert instance on which we invoke this as well as passed parameter into CartesianCoordinate
	 * Subsequently invoke primitive helper method inside CartesianCoordinate class for actual computation
	 */
	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		assertClassInvariants();
		// assert precondition to provide valid execution environment
		assertIsNonNullArgument(coordinate);
		// convert implicit this parameter on which method invoked on to cartesian
		CartesianCoordinate cartCoord = this.asCartesianCoordinate();
		// convert method argument to cartesian -- calculation will be working on cartesian only
		CartesianCoordinate cartCoord2 = coordinate.asCartesianCoordinate();
		// delegate to implementation inside CartesianCoordinate class
		double cartDistance = cartCoord.doGetCartesianDistance(cartCoord2);
		// assert postcondition: cartesian distance has to be a valid double number which is not below zero
		assert (DoubleValuesUtil.isValidDouble(cartDistance) && !(cartDistance < 0.0)) : "Cartesian distance is invalid double or below 0";
		// ensure that computation did not violate class invariants
		assertClassInvariants();
		return cartDistance;
	}

	/*
	 * @methodtype: assertion method
	 * using runtime polymorphism this will be invoked on the specific Coordinate subclass.
	 * asserts that class invariants are always maintained before and after method invocation
	 */
	protected abstract void assertClassInvariants();
	
	/*
	 * @methodtype: assertion method
	 * ensuring that no null parameters is passed as argument
	 */
	protected void assertIsNonNullArgument(Object obj) {
		if(obj == null) {
			throw new IllegalArgumentException("Passed argument parameter must not be null");
		}
	}

	/*
	 * convert instance on which we invoke this as well as passed parameter into SphericCoordinate
	 * Subsequently invoke primitive helper method inside SphericCoordinate class for actual computation
	 */
	@Override
	public double getCentralAngle(Coordinate coordinate) {
		assertClassInvariants();
		// assert precondition to provide valid execution environment
		assertIsNonNullArgument(coordinate);
		// convert implicit this parameter on which method invoked on to spheric
		SphericCoordinate sphereCoord = this.asSphericCoordinate();
		// convert method argument to spheric -- calculation will be working on spheric only
		SphericCoordinate sphereCoord2 = coordinate.asSphericCoordinate();
		// delegate to implementation inside SphericCoordinate class
		double centralAngle = sphereCoord.doGetCentralAngle(sphereCoord2);
		// assert postcondition: central angle has to be a valid double value
		assert DoubleValuesUtil.isValidDouble(centralAngle) : "Central angle is invalid double";
		// ensure that computation did not violate class invariants
		assertClassInvariants();
		return centralAngle;
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
