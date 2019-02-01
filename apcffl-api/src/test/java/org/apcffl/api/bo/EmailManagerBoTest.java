package org.apcffl.api.bo;

import static org.mockito.ArgumentMatchers.isA;

import javax.annotation.Resource;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailManagerBoTest {

	@Mock
	private JavaMailSender emailSender;

	@InjectMocks
	@Resource
	private EmailManagerBo bo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testEmailSend() {
		Mockito.doNothing().when(emailSender).send(isA(SimpleMailMessage.class));
		
		bo.sendEmail(ApcfflTest.EMAIL_RECIPIENT, ApcfflTest.EMAIL_SUBJECT, ApcfflTest.EMAIL_MESSAGE);
	}

	@Test(expected = org.apcffl.api.exception.EmailException.class)
	public void testEmailSendException() {
		Mockito.doThrow(new NullPointerException("error1")).when(emailSender).send(isA(SimpleMailMessage.class));
		
		bo.sendEmail(ApcfflTest.EMAIL_RECIPIENT, ApcfflTest.EMAIL_SUBJECT, ApcfflTest.EMAIL_MESSAGE);
	}
}
