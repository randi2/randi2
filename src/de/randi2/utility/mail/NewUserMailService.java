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
 * Service to send the registration email to new users. The message to send is
 * generated via a template.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public class NewUserMailService implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.utility.mail.MailService#sendMail(de.randi2.model.Login)
	 */
	public boolean sendMail(final Login user) throws MailErrorException {

		final String recipient = user.getPerson().getEMail();
		final String sender = "randi2@randi2.de"; // TODO outsource - if
													// possible?
		final String subject = "Welcome to RANDI2!";
		try {

			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage);
					message.setTo(recipient);
					message.setFrom(sender);
					message.setSubject(subject);

					// Map of variables
					Map model = new HashMap();
					model.put("user", user);

					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine,
							"de/randi2/utility/mail/templates/NewUserMail.vm",
							model);
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
