package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SphericCoordinateTest {

//epsilon to compare floating point values
	private static final double EPSm6 = 1e-6;
	CartesianCoordinate cartCoord1;
	CartesianCoordinate cartCoord2;
	CartesianCoordinate cartCoord3;
	CartesianCoordinate cartCoord8;

	
	SphericCoordinate sphereCoord1;
	SphericCoordinate sphereCoord2;
	SphericCoordinate sphereCoord3;
	SphericCoordinate sphereCoord4;
	SphericCoordinate sphereCoord5;
	SphericCoordinate sphereCoord6;
	
	@Before
	public void setUp() {
		cartCoord1 = new CartesianCoordinate(3, 4, 6.7);
		//equivalent to sphereCoord2
		cartCoord2 = new CartesianCoordinate(1.157200218, 1.529205418, 3.510330248);
		//equivalent to sphereCoord3
		cartCoord3 = new CartesianCoordinate(-0.8865010484, 0.01027735996, 2.866009467);

		cartCoord8 = new CartesianCoordinate(1.2328323, 3.352732, 31.2323);
		
		sphereCoord1 = new SphericCoordinate(0.64110876885971, 0.92729521800161, 8.3600239234107);
		//equivalent to cartCoord8
		sphereCoord4 = new SphericCoordinate(0.11388065015818, 1.2184323624132, 31.435922932749);
		sphereCoord5 = new SphericCoordinate(0.11388065015818, 1.2184323624132, 31.435922932749);
		//equivalent to cartCoord7
		sphereCoord6 = new SphericCoordinate(0.28779177270424, 1.5707963267949, 0.70463347047667);

		sphereCoord2 = new SphericCoordinate(0.5, 0.923, 4.0);
		sphereCoord3 = new SphericCoordinate(0.3, 3.13, 3.0);
		
	}
	
	//=================== Test getters & setters ==========================
	@Test
	public void testGetters() {
		
		double expectedPhi = 0.64110876885971;
		assertEquals(expectedPhi, sphereCoord1.getPhi(), EPSm6);
		
		double expectedTheta = 0.92729521800161;
		assertEquals(expectedTheta, sphereCoord1.getTheta(), EPSm6);
		
		double expectedRadius = 8.3600239234107;
		assertEquals(expectedRadius, sphereCoord1.getRadius() , EPSm6);
		
	}
	
	@Test
	public void testSetters() {

		sphereCoord1.setPhi(1.42323);
		assertEquals(1.42323, sphereCoord1.getPhi(), EPSm6);
		
		sphereCoord1.setTheta(2.3123123);
		assertEquals(2.3123123, sphereCoord1.getTheta(), EPSm6);
		
		sphereCoord1.setRadius(5.2312312);
		assertEquals(5.2312312, sphereCoord1.getRadius(), EPSm6);
		
	}
		
		//test setters that will provoke assertions / exceptions to be thrown
		@Test(expected = IllegalArgumentException.class)
		public void testSettersThrowException1() {
			sphereCoord1.setPhi(-1.0);
		}
		
		//boundary check of setPhi, causes exception to be thrown
		@Test(expected = IllegalArgumentException.class)
		public void testSettersThrowException2() {
			sphereCoord1.setPhi(2*Math.PI);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testSettersThrowException3() {
			sphereCoord2.setTheta(0.1 + Math.PI);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testSettersThrowException4() {
			sphereCoord2.setTheta(Double.NEGATIVE_INFINITY);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testSettersThrowException5() {
			sphereCoord3.setRadius(-1.0);
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
		
		
		//check inequality:
		@Test
		public void testNotEquals1() {
			SphericCoordinate sphereCoordinateUnequal = new SphericCoordinate(0.6, 0.923, 4.0);
			assertNotEquals(sphereCoord2,sphereCoordinateUnequal);
		}
		
		//check inequality with null value involved
		@Test
		public void testNotEquals3() {
			assertNotEquals(sphereCoord1,null);
		}
		
		//check inequality with a class that is not an instance of Coordinate
		@Test
		public void testNotEquals4() {
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
			double expected = 30.743588;
			double actual = sphereCoord6.getCartesianDistance(sphereCoord5);
			assertEquals(expected, actual, EPSm6);
		}
		
		@Test
		public void testGetCartesianDistance2() {
			double expected = 2.626596;
			double actual = sphereCoord2.getCartesianDistance(sphereCoord3);
			assertEquals(expected, actual, EPSm6);
		}
				
				
				
				
	//=================== test conversion to CartesianCoordinate ==========================
	@Test
	public void testAsCartesianCoordinate1() {
		CartesianCoordinate cartCoordConversion = sphereCoord1.asCartesianCoordinate();
		assertTrue(cartCoordConversion.equals(cartCoord1));
		assertTrue(cartCoord1.equals(cartCoordConversion));
		assertEquals(sphereCoord1.asCartesianCoordinate(), cartCoord1);
	}
	
	@Test
	public void testAsCartesianCoordinate2() {
		CartesianCoordinate cartCoordConversion2 = sphereCoord4.asCartesianCoordinate();
		assertTrue(cartCoordConversion2.equals(cartCoord8));
		assertTrue(cartCoord8.equals(cartCoordConversion2));
		assertEquals(sphereCoord4.asCartesianCoordinate(), cartCoord8);
	}
	
	@Test
	public void testAsCartesianCoordinate3() {
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

}
