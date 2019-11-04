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
package org.wahlzeit.services.mailing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.EmailAddress;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;



public class EmailServiceTest {

	EmailService emailService = null;
	EmailAddress validAddress = null;
	SmtpEmailService mail = null;

	@Before
	public void setup() throws Exception {
		emailService = EmailServiceManager.getDefaultService();
		validAddress = EmailAddress.getFromString("test@test.de");
		//ARRANGE : mock the SmtpEmailService
		mail = mock(SmtpEmailService.class);
		
	}

	@Test
	public void testSendInvalidEmail() {
		try {
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "lol", "hi"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddress, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "hi", "       "));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}

	@Test
	public void testSendValidEmail() {
		try {
			assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress, "hi", "test"));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}
	
	//test sending of mail that includes BCC 
	@Test
	public void testSendInvalidBccEmail() {
		try {
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, validAddress, "lol", "hi"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddress, validAddress, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddress, null, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, validAddress, "hi", "       "));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}
	
	//another test case checking mails including BCC
	@Test
	public void testSendValidBccEmail() {
		try {
			assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress, validAddress, "hi", "test"));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}
	
	//test that correct message is returned (here using subject) once mail created by using mock msg 
	@Test
	public void testDoCreateSmtpEmailService() throws MailingException, MessagingException {
		// ARRANGE	
		SmtpEmailService smtp = new SmtpEmailService();
		String expectedMessageSubject = "testsubject"; 
		// ACT
		 javax.mail.Message actualMessage = smtp.doCreateEmail(validAddress, validAddress, validAddress, "testsubject", "hi");
		// ASSERT
		assertEquals(expectedMessageSubject, actualMessage.getSubject());
	}
	
	//test that mailing exception is thrown when incorrect subject used
	@Test(expected = MailingException.class)
	public void testDoCreateSmtpEmailService2() throws MailingException {
		//create a subject which will be used to artificially provoke an exception
		String failedSubject = "ExceptionThrowing";
		//speficy that this particular subject provokes the exception when email created with it
		when(mail.doCreateEmail(validAddress, validAddress, validAddress, failedSubject, "test")).
		thenThrow(new MailingException("wrong"));
		//ACT
		mail.doCreateEmail(validAddress, validAddress, validAddress, failedSubject, "test");
		//ASSERT that only one mail has been called
		verify(mail, times(1)).doCreateEmail(validAddress, validAddress, validAddress, failedSubject, "test");
	}
	
	//test that an exception is thrown when invalid message is sent
	@Test(expected = MailingException.class)
	public void testDoSendSmtpEmailService() throws MailingException {
		//ARRANGE: mock a MimeMessage object to test a failed smtp mail sending procedure
		MimeMessage msg = mock(MimeMessage.class);
		//speficy that this msg object provokes the exception when email sent with it
		doThrow(new MailingException("Sending failed")).when(mail).doSendEmail(msg);
		//ACT
		mail.doSendEmail(msg);
		//ASSERT that only mail has only been tried to send only once
		verify(mail, times(1)).doSendEmail(msg);
	}

	
}