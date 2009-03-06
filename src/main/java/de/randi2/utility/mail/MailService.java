package de.randi2.utility.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import de.randi2.model.Login;
import de.randi2.utility.mail.exceptions.MailErrorException;

/**
 * Service to send emails. The message to send is
 * generated using a template.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public class MailService implements MailServiceInterface {

	@Autowired
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.utility.mail.MailService#sendMail(de.randi2.model.Login)
	 */
	public boolean sendMail(final String recipient, final String subject, final String messageTemplate, final Map<String,Object> templateFields) throws MailErrorException {

		
		final String sender = "randi2@randi2.de"; // TODO outsource - if
													// possible?
		
		try {

			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage);
					message.setTo(recipient);
					message.setFrom(sender);
					message.setSubject(subject);


					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine,
							"de/randi2/utility/mail/templates/"+messageTemplate,
							templateFields);
					message.setText(text, true);
				}
			};

			this.mailSender.send(preparator);

		} catch (Exception e) {

			throw new MailErrorException("Error while sending email..");

		}

		return true;

	}

}
