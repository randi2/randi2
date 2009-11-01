package de.randi2.utility.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import de.randi2.utility.mail.exceptions.MailErrorException;

/**
 * Service to send e-mails. The message to send is generated using a template.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public class MailService implements MailServiceInterface {

	private static final String PATH_MAIL_TEMPLATES = "mail/templates";
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;

	private String from;
	
	private String hoster;
	private String url;



	public String getHoster() {
		return hoster;
	}

	public void setHoster(String hoster) {
		this.hoster = hoster;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.utility.mail.MailService#sendMail(de.randi2.model.Login)
	 */
	public boolean sendMail(final String recipient,
			final String messageTemplate, final Locale templateLanguage,
			final Map<String, Object> messageFields,
			final Map<String, Object> subjectFields) throws MailErrorException {

		try {
			messageFields.put("hoster", hoster);
			messageFields.put("url", url);
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage);
					message.setTo(recipient);
					message.setFrom(from);

					String subject = VelocityEngineUtils
							.mergeTemplateIntoString(velocityEngine,
									PATH_MAIL_TEMPLATES
											+ templateLanguage.getLanguage()
													.toLowerCase(Locale.getDefault()) + "/"
											+ messageTemplate + "_subject.vm",
									subjectFields);
					message.setSubject(subject);

					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, PATH_MAIL_TEMPLATES
									+ templateLanguage.getLanguage()
											.toLowerCase(Locale.getDefault()) + "/"
									+ messageTemplate + ".vm", messageFields);
					message.setText(text, true);
				}
			};

			this.mailSender.send(preparator);

		} catch (Exception e) {
			throw new MailErrorException("Error while sending email..");

		}

		return true;

	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
