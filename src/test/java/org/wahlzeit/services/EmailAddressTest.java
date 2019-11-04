/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.services;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Test cases for the EmailAddress class.
 */
public class EmailAddressTest extends TestCase {
	
	private EmailAddress ea;
	/*
	//set up working email address instance for various following test cases
	@Before
	public void setUpWorkingEmailAddress() {
		ea = new EmailAddress("max_mustermann@mail.de");
	}
	*/
	/**
	 *
	 */
	public EmailAddressTest(String name) {
		super(name);
	}

	/**
	 *
	 */
	public void testGetEmailAddressFromString() {
		// invalid email addresses are allowed for local testing and online avoided by Google

		assertTrue(createEmailAddressIgnoreException("bingo@bongo"));
		assertTrue(createEmailAddressIgnoreException("bingo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo.bongo@bongo.com"));
		assertTrue(createEmailAddressIgnoreException("bingo+bongo@bango"));
	}

	/**
	 *
	 */
	protected boolean createEmailAddressIgnoreException(String ea) {
		try {
			EmailAddress.getFromString(ea);
			return true;
		} catch (IllegalArgumentException ex) {
			// creation failed
			return false;
		}
	}

	/**
	 *
	 */
	public void testEmptyEmailAddress() {
		assertFalse(EmailAddress.EMPTY.isValid());
	}
	
	/**
	 * test asString() method 
	 */
	@Test
	public void testEmailAdressValue() {
		EmailAddress ea = new EmailAddress("max_mustermann@mail.de");
		assertTrue(ea.asString().equals("max_mustermann@mail.de"));
	}
	
	/**
	 * test broken email address and assert that correct exception thrown
	 */
	@Test(expected = AddressException.class)
	public void testBrokenInternetAddress() {
		EmailAddress mail = new EmailAddress(";");
		InternetAddress ia = mail.asInternetAddress();
	}
	
	/**
	 * test working email address and assert that NO exception thrown
	 */
	@Test(expected = Test.None.class)
	public void testWorkingInternetAddress() {
		EmailAddress ea = new EmailAddress("www.internet.de");
		InternetAddress ia = ea.asInternetAddress();
	}
	
	/**
	 * test isEqual() method which works based on equal references
	 */
	public void testIsEqualEmailAdress() {
		EmailAddress ea = new EmailAddress("www.internet.de");
		assertTrue(ea.isEqual(ea));
	}
	
	
	/**
	 * test failing isEqual() method given two unequal references
	 */
	public void testIsNotEqualEmailAdress() {
		EmailAddress ea = new EmailAddress("www.internet.de");
		assertFalse(ea.isEqual(new EmailAddress("")));
	}
	
	/**
	 * test failing isValid / isEmpty method
	 */
	public void testIsNotValidEmailAddress() {
		assertFalse(EmailAddress.EMPTY.isValid());
	}
	
	/**
	 * test working isVaid / is Empty method
	 */
	public void testIsValidEmailAddress() {
		EmailAddress ea =  new EmailAddress("www.internet.de");
		assertTrue(ea.isValid());
	}

}

