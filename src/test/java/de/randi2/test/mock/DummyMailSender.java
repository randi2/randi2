package de.randi2.test.mock;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Provides a dummy JavaMailSender interface.
 * 
 * 
 * @author Daniel Haehn <dh@randi2.de>
 *
 */
public class DummyMailSender implements JavaMailSender {

	@Override
	public MimeMessage createMimeMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MimeMessage createMimeMessage(InputStream arg0) throws MailException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(MimeMessage arg0) throws MailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(MimeMessage[] arg0) throws MailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(MimeMessagePreparator arg0) throws MailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(MimeMessagePreparator[] arg0) throws MailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(SimpleMailMessage arg0) throws MailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(SimpleMailMessage[] arg0) throws MailException {
		// TODO Auto-generated method stub

	}

}
