/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.utility.mail;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;


import de.randi2.utility.mail.exceptions.MailErrorException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Service to send e-mails. The message to send is generated using a template.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public class MailService implements MailServiceInterface {

	private static final String PATH_MAIL_TEMPLATES = "mail/templates/";
	
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
			final String messageTemplate, final Locale templateLanguageParam,
			final Map<String, Object> messageFields,
			final Map<String, Object> subjectFields) throws MailErrorException {

		try {
			final Locale templateLanguage = (templateLanguageParam != null) ? templateLanguageParam : Locale.ENGLISH;
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
			throw new MailErrorException(String.format("Error while sending mail: '%s'", e.getMessage()), e);

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
