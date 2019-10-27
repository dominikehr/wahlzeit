package org.wahlzeit.model;

import java.util.Objects;

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
	public double getDistance(Coordinate coordinate) {
		return Math.sqrt(
				Math.pow(this.x - coordinate.getX(), 2)
				+ Math.pow(this.y - coordinate.getY(), 2) 
				+ Math.pow(this.z - coordinate.getZ(), 2)
						);
	}

	//overwrite equals() method and forward to isEqual()
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		
		if(o == null) {
			return false;
		}
		
		if(o instanceof Coordinate) {
			Coordinate coord = (Coordinate) o;
			//forward to isEqual to check equality based on our definitions
			return isEqual(coord);
		}
		
		return false;
	}
	
	//our custom-built equality contract determining equality based on equal coordinate variables
	public boolean isEqual(Coordinate coordinate) {
		return this.x == coordinate.getX() && this.y == coordinate.getY() && this.z == coordinate.getZ();
	}
	
	//override hashCode
	//two equal classes --based on our custom equality contract -- will have equal hashes
	@Override
	public int hashCode() {
		int hash = 7;
		//incorporate all 3 fields defining equality into the hashCode computation
		hash = 31 * hash + ((this.x == 0) ? 0 : Objects.hashCode(this.x));
		hash = 31 * hash + ((this.y == 0) ? 0 : Objects.hashCode(this.y));
		hash = 31 * hash + ((this.z == 0) ? 0 : Objects.hashCode(this.z));
		return hash;
	}
	
}
