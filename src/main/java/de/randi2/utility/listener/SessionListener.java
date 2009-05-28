package de.randi2.utility.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.impl.SessionImpl;

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
