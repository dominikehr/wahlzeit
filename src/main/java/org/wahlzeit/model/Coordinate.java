package org.wahlzeit.model;


public class Coordinate {
	
	//three private coordinate Variables reflecting x,y,z dimensions
	private double x;
	private double y;
	private double z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//getters and setter for the 3 coordinate dimensions
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
	
	//returns the Cartesian distance between two points
	protected double getDistance(Coordinate coordinate) {
		return Math.sqrt(
				Math.pow(this.x - coordinate.getX(), 2)
				+ Math.pow(this.y - coordinate.getY(), 2) 
				+ Math.pow(this.z - coordinate.getZ(), 2)
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
		Coordinate coord = (Coordinate) obj;
		return isEqual(coord);
	}
	
	//our custom-built equality contract determining equality based on equal coordinate variables
	protected boolean isEqual(Coordinate coordinate) {
		//check if null in case direct call to this method
		if(coordinate == null) {
			return false;
		}
		boolean eqX = compareDoubles(this.getX() , coordinate.getX());
		boolean eqY = compareDoubles(this.getY(), coordinate.getY());
		boolean eqZ = compareDoubles(this.getZ(), coordinate.getZ());
		//if all three equal then equality between two coordinates is given
		return eqX && eqY && eqZ;
	}
	
	//epsilon value to compare doubles
	private static final double EPSm15 = 1e-15;
	private static boolean compareDoubles(double firstDim, double secondDim) {
		//if either one of the numbers compared is NaN return false
		if(Double.isNaN(firstDim) || Double.isNaN(secondDim)) {
			return false;
		}
		//check if double values can be considered equal according to specified precision
		return Math.abs(firstDim - secondDim) < EPSm15; 
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
	
}
