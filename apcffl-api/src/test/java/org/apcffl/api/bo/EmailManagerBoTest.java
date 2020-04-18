package org.apcffl.api.bo;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.EmailConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {EmailConfig.class})
//@SpringBootTest(classes = {EmailConfig.class,RepositoryConfig.class})
public class EmailManagerBoTest {
	
	@SuppressWarnings("unused")
	@Autowired
	private EmailConfig emailConfig;

	@Mock
	private JavaMailSender javaMailSender;

	private EmailManagerBo bo;

	@Captor
	private ArgumentCaptor<SimpleMailMessage> emailCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		bo = new EmailManagerBo(javaMailSender);
	}
	
	@Test
	public void verify_sendEmail() {
		
		// prepare test data
		
		Mockito.doNothing().when(javaMailSender).send(isA(SimpleMailMessage.class));
		
		// invoke
		
		bo.sendEmail(ApcfflTest.EMAIL_RECIPIENT, ApcfflTest.EMAIL_SUBJECT, ApcfflTest.EMAIL_MESSAGE);
		
		// verify
		
		verify(javaMailSender, times(1)).send(emailCaptor.capture());
		assertEquals(ApcfflTest.EMAIL_RECIPIENT, emailCaptor.getValue().getTo()[0]);
		assertEquals(ApcfflTest.EMAIL_SUBJECT, emailCaptor.getValue().getSubject());
		assertEquals(ApcfflTest.EMAIL_MESSAGE, emailCaptor.getValue().getText());
	}

	@Test(expected = org.apcffl.api.exception.EmailException.class)
	public void testEmailSendException() {
		
		// prepare test data
		
		Mockito.doThrow(new NullPointerException("error1")).when(javaMailSender).send(isA(SimpleMailMessage.class));
		
		// invoke
		
		bo.sendEmail(ApcfflTest.EMAIL_RECIPIENT, ApcfflTest.EMAIL_SUBJECT, ApcfflTest.EMAIL_MESSAGE);
		
		// verify
		
		verify(javaMailSender, times(1)).send(emailCaptor.capture());
		assertEquals(ApcfflTest.EMAIL_RECIPIENT, emailCaptor.getValue().getTo()[0]);
		assertEquals(ApcfflTest.EMAIL_SUBJECT, emailCaptor.getValue().getSubject());
		assertEquals(ApcfflTest.EMAIL_MESSAGE, emailCaptor.getValue().getText());
	}
}
