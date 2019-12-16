package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.customexceptions.CoordinateConversionException;

public class SphericCoordinateTest {

//epsilon to compare floating point values
	private static final double EPSm6 = 1e-6;
	AbstractCoordinate cartCoord1;
	AbstractCoordinate cartCoord2;
	AbstractCoordinate cartCoord3;
	AbstractCoordinate cartCoord6;
	AbstractCoordinate cartCoord7;
	AbstractCoordinate cartCoord8;

	
	AbstractCoordinate sphereCoord1;
	AbstractCoordinate sphereCoord2;
	AbstractCoordinate sphereCoord3;
	AbstractCoordinate sphereCoord4;
	AbstractCoordinate sphereCoord5;
	AbstractCoordinate sphereCoord6;
	AbstractCoordinate sphereCoord7;
	AbstractCoordinate sphereCoord8;
	
	@Before
	public void setUp() {
		//equivalent to sphereCoord1
		cartCoord1 = CartesianCoordinate.getInstance(3, 4, 6.7);
		//equivalent to sphereCoord2
		cartCoord2 = CartesianCoordinate.getInstance(1.157200218, 1.529205418, 3.510330248);
		//equivalent to sphereCoord3
		cartCoord3 = CartesianCoordinate.getInstance(-0.8865010484, 0.01027735996, 2.866009467);
		//equivalent to sphereCoord6
		cartCoord6 = CartesianCoordinate.getInstance(12,3,9);
			//equivalent to sphereCoord7
		cartCoord7 = CartesianCoordinate.getInstance(0.0,2.0,0.675654);
		//equivalent to sphereCoord8
		cartCoord8 = CartesianCoordinate.getInstance(1.2328323, 3.352732, 31.2323);
		
		//equivalent to cartCoord1
		sphereCoord1 = SphericCoordinate.getInstance(0.64110876885971, 0.92729521800161, 8.3600239234107);
		//equivalent to cartCoord2
		sphereCoord2 = SphericCoordinate.getInstance(0.5, 0.923, 4.0);
		//equivalent to cartCoord3
		sphereCoord3 = SphericCoordinate.getInstance(0.3, 3.13, 3.0);
		//equivalent to cartCoord8
		sphereCoord4 = SphericCoordinate.getInstance(0.11388065015818, 1.2184323624132, 31.435922932749);
		sphereCoord5 = SphericCoordinate.getInstance(0.11388065015818, 1.2184323624132, 31.435922932749);
		//equivalent to cartCoord7
		sphereCoord7 = SphericCoordinate.getInstance(1.2450069391398, 1.5707963267949, 2.1110443689596);
		// equivalent to cartCoord8
		sphereCoord8 = SphericCoordinate.getInstance(0.11388065015818, 1.2184323624132, 31.435922932749);	
	}		
	//=================== check isEqual() method ==========================
	//check equality of two coordinates
	@Test
	public void testEquals1() {			
		assertEquals(sphereCoord4,sphereCoord5);
	}
	
	//check equality of the very same instance
	@Test
	public void testEquals3() {
		assertEquals(sphereCoord1,sphereCoord1);
	}
	
	
	//check equality between SphericCoordinate and CartesianCoordinate that have exact same values after conversion
	@Test
	public void testEqualsSphereCoordCartCoord() {
		assertEquals(sphereCoord2, cartCoord2);
	}
	
	//check inequality:
	@Test
	public void testNotEquals1() {
		SphericCoordinate sphereCoordinateUnequal = SphericCoordinate.getInstance(0.6, 0.923, 4.0);
		assertNotEquals(sphereCoord2,sphereCoordinateUnequal);
	}
	
	//check inequality with null value involved
	@Test
	public void testNotEquals2() {
		assertNotEquals(sphereCoord1,null);
	}

	//check inequality with a class that is not an instance of Coordinate
	@Test
	public void testNotEquals3() {
		String test = "test";
		assertNotEquals(sphereCoord5,test);
	}
	
	//check equality of hashCode
	@Test
	public void testHashCode1() {	
		//two equal coordinates have to have equals hash codes
		assertEquals(sphereCoord4.hashCode(),sphereCoord5.hashCode());
	}
								
			
//=================== check cartesian distance method ==========================
	@Test
	public void testGetCartesianDistance1() {
		double expected = 30.611409;
		double actual = sphereCoord7.getCartesianDistance(sphereCoord8);
		assertEquals(expected, actual, EPSm6);
	}
	
	@Test
	public void testGetCartesianDistance2() {
		double expected = 2.626596;
		double actual = sphereCoord2.getCartesianDistance(sphereCoord3);
		assertEquals(expected, actual, EPSm6);
	}
			
	/*	check interchangeability of coordinate class:
	 * use different Coordinate types to invoke on / pass as parameter
	 * 
	 */
	@Test
	public void testGetCartesianDistance3() {
		double expected = 2.626596;
		double actual = cartCoord2.getCartesianDistance(sphereCoord3);
		assertEquals(expected, actual, EPSm6);
	}
	
	@Test 
	public void testGetCartesianDistance4(){
		double expected = 30.611409;
		double actual = sphereCoord7.getCartesianDistance(cartCoord8);
		assertEquals(expected, actual, EPSm6);
	}
	
	//test whether exception is thrown once null parameter passed
	@Test(expected = IllegalArgumentException.class)
	public void testGetCartesianDistanceWithNull() {
		sphereCoord7.getCartesianDistance(null);
	}
	
					
				
	//=================== test conversion to CartesianCoordinate ==========================
	@Test
	public void testAsCartesianCoordinate1() throws CoordinateConversionException {
		CartesianCoordinate cartCoordConversion = sphereCoord1.asCartesianCoordinate();
		assertTrue(cartCoordConversion.equals(cartCoord1));
		assertTrue(cartCoord1.equals(cartCoordConversion));
		assertEquals(sphereCoord1.asCartesianCoordinate(), cartCoord1);
	}
	
	@Test
	public void testAsCartesianCoordinate2() throws CoordinateConversionException {
		CartesianCoordinate cartCoordConversion2 = sphereCoord4.asCartesianCoordinate();
		assertTrue(cartCoordConversion2.equals(cartCoord8));
		assertTrue(cartCoord8.equals(cartCoordConversion2));
		assertEquals(sphereCoord4.asCartesianCoordinate(), cartCoord8);
	}
	
	@Test
	public void testAsCartesianCoordinate3() throws CoordinateConversionException {
		CartesianCoordinate cartCoordConversion3 = sphereCoord3.asCartesianCoordinate();
		assertTrue(cartCoordConversion3.equals(cartCoord3));
		assertTrue(cartCoord3.equals(cartCoordConversion3));
		assertEquals(sphereCoord3.asCartesianCoordinate(), cartCoord3);
	}
	
	//=================== test getCentralAngle ==========================
	
	@Test
	public void testGetCentralAngle1() {
		double expected = 1.93525571446091;
		assertEquals(expected, sphereCoord2.getCentralAngle(sphereCoord3), EPSm6);
	}
	
	@Test
	public void testGetCentralAngle2() {
		double expected = 0.590436677318338;
		assertEquals(expected, sphereCoord1.getCentralAngle(sphereCoord4), EPSm6);
	}
	
	/*	check interchangeability of coordinate class:
	 * use different Coordinate types to invoke on / pass as parameter
	 * 
	 */
	@Test
	public void testGetCentralAngle3() {
		double expected = 1.93525571446091;
		assertEquals(expected, sphereCoord2.getCentralAngle(cartCoord3), EPSm6);
	}
	
	@Test
	public void testGetCentralAngle4() {
		double expected = 0.590436677318338;
		assertEquals(expected, cartCoord1.getCentralAngle(sphereCoord4), EPSm6);
	}
	
	//test whether exception is thrown once null parameter passed
	@Test(expected = IllegalArgumentException.class)
	public void testGetCentralAngleWithNull() {
		sphereCoord2.getCentralAngle(null);
	}

}
