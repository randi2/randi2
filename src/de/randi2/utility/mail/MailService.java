package de.randi2.utility.mail;

import de.randi2.model.Login;
import de.randi2.utility.mail.exceptions.MailErrorException;

/**
 * This interface provides methods to send emails to a user.
 * 
 * @author Daniel Haehn <dh@randi2.de>
 * 
 */
public interface MailService {

	/**
	 * Sends an E-Mail to the given Login object / user.
	 * 
	 * @param user The recipient as a Login object (final because of inner class in this method)
	 * @return TRUE or FALSE
	 * @throws MailErrorException
	 *             In case of any error.
	 */
	public boolean sendMail(final Login user) throws MailErrorException;

}
