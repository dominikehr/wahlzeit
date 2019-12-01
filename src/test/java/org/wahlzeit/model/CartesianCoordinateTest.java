package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CartesianCoordinateTest {

	//epsilon to compare floating point values
	private static final double EPSm6 = 1e-6;
	AbstractCoordinate cartCoord1;
	AbstractCoordinate cartCoord2;
	AbstractCoordinate cartCoord3;
	AbstractCoordinate cartCoord4;
	AbstractCoordinate cartCoord5;
	AbstractCoordinate cartCoord6;
	AbstractCoordinate cartCoord7;
	AbstractCoordinate cartCoord8;
	
	AbstractCoordinate sphereCoord1;
	AbstractCoordinate sphereCoord2;
	AbstractCoordinate sphereCoord3;
	AbstractCoordinate sphereCoord5;
	AbstractCoordinate sphereCoord6;
	AbstractCoordinate sphereCoord7;
	AbstractCoordinate sphereCoord8;
	
	
	@Before
	public void setUp() {
		cartCoord1 = new CartesianCoordinate(3, 4, 6.7);
		cartCoord2 = new CartesianCoordinate(3, 4, 6.7);
		cartCoord3 = new CartesianCoordinate(0,-4,6.7);
		cartCoord4 = new CartesianCoordinate(0,-4,6.7);
		cartCoord5 = new CartesianCoordinate(4.2,0,-1);
		cartCoord6 = new CartesianCoordinate (12,3,9);
		cartCoord7 = new CartesianCoordinate(0.0,2.0,0.675654);
		cartCoord8 = new CartesianCoordinate(1.2328323, 3.352732, 31.2323);
		
		sphereCoord1 = new SphericCoordinate(0.64110876885971, 0.92729521800161, 8.3600239234107);
		sphereCoord5 = new SphericCoordinate(1.8045395076638, 0, 4.3174066289846);
		sphereCoord6 = new SphericCoordinate(0.94178152436822, 0.24497866312686, 15.297058540778);
		sphereCoord7 = new SphericCoordinate(1.2450069391398, 1.5707963267949, 2.1110443689596);
		sphereCoord8 = new SphericCoordinate(0.11388065015818, 1.2184323624132, 31.435922932749);
		
		// 2 and 3 do not correspond to any particular cartCoords 
		sphereCoord2 = new SphericCoordinate(0.5, 0.923, 4.0);
		sphereCoord3 = new SphericCoordinate(0.3, 3.13, 3.0);
	}

	//=================== check isEqual() method ==========================
	
	//check equality of two coordinates
	@Test
	public void testEquals1() {			
		assertEquals(cartCoord1,cartCoord2);
	}
	
	//another equality test case this time including boundary values like 0 and -4
	@Test
	public void testEquals2() {
		assertEquals(cartCoord1,cartCoord2);
	}
	
	//check equality of the very same instance
	@Test
	public void testEquals3() {
		assertEquals(cartCoord1,cartCoord1);
	}
	
	//check equality between CartesianCoordinate and SphericCoordinate that have exact same values after conversion
	@Test
	public void testEquals4() {
		assertEquals(cartCoord1, sphereCoord1);
	}
	
	//check inequality:
	@Test
	public void testNotEquals1() {
		CartesianCoordinate coordUnequal1 = new CartesianCoordinate(0,-4,6.6);
		assertNotEquals(cartCoord1,coordUnequal1);
	}
	
	//check inequality with null value involved
	@Test
	public void testNotEquals2() {
		assertNotEquals(cartCoord3,null);
	}
	
	//check inequality with a class that is not an instance of Coordinate
	@Test
	public void testNotEquals3() {
		String test = "test";
		assertNotEquals(cartCoord5,test);
	}

	//check equality of hashCode
	@Test
	public void testHashCode1() {	
		//two equal coordinates have to have equals hash codes
		assertEquals(cartCoord1.hashCode(),cartCoord2.hashCode());
	}
	
	//check equality of hashCode with boundary values
	@Test
	public void testHashCode2() {
		//two equal coordinates have to have equals hash codes
		assertEquals(cartCoord3.hashCode(), cartCoord4.hashCode());
	}
	
	//=================== check cartesian distance method ==========================
	@Test 
	public void testGetCartesianDistance1(){			
		double expected = 13.032268;
		double actual = cartCoord6.getCartesianDistance(cartCoord5);
		assertEquals(expected, actual, EPSm6);
		
	}
	
	@Test 
	public void testGetCartesianDistance2(){
		double expected = 30.611409;
		double actual = cartCoord7.getCartesianDistance(cartCoord8);
		assertEquals(expected, actual, EPSm6);
		
	}
			
	//test only 0 boundary value:
	@Test
	public void testGetCartesianDistance3(){
		CartesianCoordinate coordNull1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		CartesianCoordinate coordNull2 = new CartesianCoordinate(0.0, 0.0, 0.0);
		
		double expected = 0.0;
		double actual = coordNull1.getCartesianDistance(coordNull2);
		assertEquals(expected, actual, EPSm6);
	}
	
	
	/*	check interchangeability of coordinate class:
	 * use different Coordinate types to invoke on / pass as parameter
	 * 
	 */
	@Test 
	public void testGetCartesianDistance4(){
		double expected = 13.032268;
		double actual = sphereCoord5.getCartesianDistance(cartCoord6);
		assertEquals(expected, actual, EPSm6);
	}
	
	@Test 
	public void testGetCartesianDistance5(){
		double expected = 30.611409;
		double actual = cartCoord7.getCartesianDistance(sphereCoord8);
		assertEquals(expected, actual, EPSm6);
		
	}
	
	//=================== check conversion to SphericCoordinate ==========================
	@Test
	public void testAsSphericCoordinate1() {
		SphericCoordinate sphereCoordConversion = cartCoord1.asSphericCoordinate();
		assertTrue(sphereCoordConversion.equals(sphereCoord1));
		assertTrue(sphereCoord1.equals(sphereCoordConversion));
		assertEquals(cartCoord1.asSphericCoordinate(), sphereCoord1);
	}
	
	@Test
	public void testAsSphericCoordinate2() {
		SphericCoordinate sphereCoordConversion2 = cartCoord8.asSphericCoordinate();
		assertTrue(sphereCoordConversion2.equals(sphereCoord8));
		assertTrue(sphereCoord8.equals(sphereCoordConversion2));
		assertEquals(cartCoord8.asSphericCoordinate(), sphereCoord8);
	}
	
	@Test
	public void testAsSphericCoordinate3() {
		//equivalent to sphereCoord3
		CartesianCoordinate cartCoord3 = new CartesianCoordinate(-0.8865010484, 0.01027735996, 2.866009467);
		SphericCoordinate sphereCoordConversion3 = cartCoord3.asSphericCoordinate();
		assertTrue(sphereCoordConversion3.equals(sphereCoord3));
		assertTrue(sphereCoord3.equals(sphereCoordConversion3));
		assertEquals(cartCoord3.asSphericCoordinate(), sphereCoord3);
	}
	
	//provoke assertion by working with 0.0 x-value which would lead to division by zero 
	@Test(expected = IllegalArgumentException.class)
	public void testAsSphericCoordinateException() {
		cartCoord3.asSphericCoordinate();
	}
	
	//=================== test getCentralAngle ==========================
	
	//test centralAngle between cartCoord5 and cartCoord6
	@Test
	public void testGetCentralAngle1() {
		double expected = 0.857389408080394;
		assertEquals(expected, cartCoord5.getCentralAngle(cartCoord6), EPSm6);
	}
	
	//test centralAngle between cartCoord1 and cartCoord8
	@Test
	public void testGetCentralAngle2() {
		double expected = 0.590436677318338;
		assertEquals(expected, cartCoord1.getCentralAngle(cartCoord8), EPSm6);
	}
	
	/*	check interchangeability of coordinate class:
	 * use different Coordinate types to invoke on / pass as parameter
	 * 
	 */
	@Test
	public void testGetCentralAngle3() {
		double expected = 0.857389408080394;
		assertEquals(expected, sphereCoord5.getCentralAngle(cartCoord6), EPSm6);
	}
	
	@Test
	public void testGetCentralAngle4() {
		double expected = 0.590436677318338;
		assertEquals(expected, cartCoord1.getCentralAngle(sphereCoord8), EPSm6);
	}

}
