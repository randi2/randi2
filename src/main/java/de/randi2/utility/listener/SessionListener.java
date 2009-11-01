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
package de.randi2.utility.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import static de.randi2.utility.webfilter.HibernateWebFilter.*;

public class SessionListener implements HttpSessionListener {

	private Logger logger = Logger.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession httpSession = se.getSession();
		Session hibernateSession = (Session) se.getSession().getAttribute(
				HIBERNATE_SESSION_KEY);
		if (hibernateSession != null && hibernateSession.isOpen()) {
			try {

				hibernateSession.flush();
				hibernateSession.close(); // Unbind is automatic here
				httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);
				// SecurityContextHolder.getContext().setAuthentication(null);
				if (httpSession.getAttribute(END_OF_CONVERSATION_FLAG) != null) {
					httpSession.removeAttribute(END_OF_CONVERSATION_FLAG);
				}
				logger.debug("Hibernate session closed (http session: " +httpSession.getId() + ")");
			} catch (IllegalStateException e) {
				hibernateSession.flush();
				hibernateSession.close(); // Unbind is automatic here
				// SecurityContextHolder.getContext().setAuthentication(null);
				logger.debug("Hibernate session closed (http session: " +httpSession.getId() + ")");
			}

		}
	}

}
