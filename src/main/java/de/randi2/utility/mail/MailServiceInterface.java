package de.randi2.utility.mail;

import java.util.Map;

import de.randi2.utility.mail.exceptions.MailErrorException;

/**
 * This interface provides methods to send emails to a user.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public interface MailServiceInterface {

	/**
	 * Sends an E-Mail using the given values .
	 * 
	 * @param recipient The recipient's email address. Can be multiple recipients.
	 * @param subject The subject of the email message.
	 * @param messageTemplate The filename (inclusing .vm extension) of the mail template which has to exist in de/randi2/utility/mail/templates/ (e.g. "NewUserMail.vm") 
	 * @param templateFields Fields which get parsed into the template. First item is the identifier and the second item is the value. (e.g. hashmap.put("username", username))
	 * @return TRUE or FALSE
	 * @throws MailErrorException
	 *             In case of any error.
	 */
	public boolean sendMail(final String recipient, final String subject, final String messageTemplate, final Map<String,Object> templateFields) throws MailErrorException;

}
