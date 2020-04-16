package org.apcffl.api.bo;

import org.apcffl.api.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailManagerBo {

	private static final Logger LOG = LoggerFactory.getLogger(EmailManagerBo.class);

    private final JavaMailSender javaMailSender;
    
    public EmailManagerBo(final JavaMailSender javaMailSender) {
    	this.javaMailSender = javaMailSender;
    }
	
	public void sendEmail(String recipient, String subject, String message) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message);
		try {
			javaMailSender.send(email);
		} catch (Exception e) {
			String error = "The email could not be sent for recipient: " + 
					recipient + ", subject: " + subject + ", message" + message;
			LOG.error(error, e);
			throw new EmailException(error);
		}
	}
}
