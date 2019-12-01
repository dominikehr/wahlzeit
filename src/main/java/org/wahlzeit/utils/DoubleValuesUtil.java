/*
 * Utility class which provides helper methods dealing with double values
 * Declared final as to avoid subclassing
 */
package org.wahlzeit.utils;


public final class DoubleValuesUtil {
	
	//epsilon value to compare doubles
	public static final double EPSm8 = 1e-8;
	
	//private constructor as to avoid instantiation
	private DoubleValuesUtil() {}

	/*
	 * @methodtype: comparison method
	 */
	public static boolean compareDoubles(double firstDim, double secondDim) {
		//if either one of the numbers compared is NaN return false
		if(Double.isNaN(firstDim) || Double.isNaN(secondDim)) {
			return false;
		}
		//check if double values can be considered equal according to specified precision
		return Math.abs(firstDim - secondDim) < EPSm8; 
	}
	
	/*
	 * @methodtype: assertion method
	 */
	public static void assertDoubleIsNotZero(double d) {
		if(d >= - EPSm8 && d <= EPSm8) {
			throw new IllegalArgumentException("Denominator of division is zero");
		}
	}
	
	/*
	 * @methodtype query method
	 */
	public static boolean isValidDouble(double d) {
		if(Double.isNaN(d) || Double.isInfinite(d)) {
			return false;
		}
		return true;
	}
}
