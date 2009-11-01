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

import java.util.Locale;
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
	 * @param recipient
	 *            The recipient's email address. Can be multiple recipients.
	 * @param messageTemplate
	 *            The filename (without .vm extension) of the mail template
	 *            which has to exist in
	 *            de/randi2/utility/mail/templates/$LANGUAGE$/ (e.g.
	 *            "NewUserMail"). The adequate Subject Template File has to
	 *            exist too (e.g. "NewUserMail_subject.vm").
	 * @param templateLanguage
	 *            The language of the desired template.
	 * @param messageFields
	 *            Fields which get parsed into the template. First item is the
	 *            identifier and the second item is the value. (e.g.
	 *            hashmap.put("username", username))
	 * @param subjectFields
	 *            Fields which get parsed into the subject. First item is the
	 *            identifier and the second item is the value. (e.g.
	 *            hashmap.put("username", username))
	 * @return TRUE or FALSE
	 * @throws MailErrorException
	 *             In case of any error.
	 */
	public boolean sendMail(final String recipient,
			final String messageTemplate, final Locale templateLanguage,
			final Map<String, Object> messageFields,
			final Map<String, Object> subjectFields) throws MailErrorException;

}
