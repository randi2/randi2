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
package de.randi2.utility.webfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This filter manages the hibernateSession to user object relation.
 * <b>CONVERSATION PATTERN</b>
 * 
 * @author Lukasz Plotnicki <lp@randi2.de> & Daniel Schrimpf <ds@randi2.de>
 * 
 */
public class HibernateWebFilter implements Filter {

	private final Logger logger = Logger.getLogger(HibernateWebFilter.class);
	/**
	 * Session Factory injected via spring.
	 */
	private SessionFactory sf;

	/**
	 * Identifier for the hibernateSession within the httpSession
	 */
	public static final String HIBERNATE_SESSION_KEY = "hibernateSession";
	/**
	 * Use this attribute to end the conversation and detach the hibernate
	 * session
	 */
	public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";

	/**
	 * The HIBERNATE SESSION
	 */
	private Session hibernateSession;
	/**
	 * The HTTP SESSION
	 */
	private HttpSession httpSession;

	/**
	 * Response object which will be used for the redirects
	 */
	private HttpServletResponse response;

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		httpSession = ((HttpServletRequest) request).getSession();
		hibernateSession = (Session) httpSession
				.getAttribute(HIBERNATE_SESSION_KEY);
		this.response = (HttpServletResponse) response;
		logger.trace("HibernateWebFilter | " + httpSession.getId());
		ensureHibernateSessionExistance();
		/*
		 * Go and do the work ...
		 */
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			invalidateOnError(e, this.response);
			return;
		}
		storeOrCloseHibernateSession();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		sf = wac.getBean(SessionFactory.class);

	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * This method contains the logic which will be executed after forrwording
	 * the request to its destination. It tries to find the exisiting
	 * hibernateSession if attached to the httpSession - in other case it
	 * creates a new one.
	 */
	private void ensureHibernateSessionExistance() {
		if (hibernateSession != null) {
			/*
			 * A hibernateSession has been found in the current httpSession
			 */
			if (!hibernateSession.isOpen()) {
				/*
				 * The found session is closed - a new will be created/opened -
				 * the conversation begins
				 */
				createHibernateSession();
			} else {
				/*
				 * Continue the conversation
				 */
				logger.trace("HibernateWebFilter | " + httpSession.getId()
						+ " << Continuing conversation ");
			}
		} else {
			/*
			 * If the httpSession doesn't contain the hibernateSession a new one
			 * will be created
			 */
			createHibernateSession();
		}
		/*
		 * Bind the hibernateSession
		 */
		ManagedSessionContext
				.bind((org.hibernate.classic.Session) hibernateSession);
	}

	/**
	 * Checks if the conversation is over - if so the hibernateSession will be
	 * closed and flushed and also detached from the httpSession. In other case
	 * it will be reattached to the httpSession.
	 */
	private void storeOrCloseHibernateSession() {
		/*
		 * End or continue the long-running conversation?
		 */
		try {
			/*
			 * END_OF_CONVERSATION_FLAG found - the hibernate session will be
			 * detached
			 */
			if (httpSession.getAttribute(END_OF_CONVERSATION_FLAG) != null) {
				closeHibernateSession();
			}
			/*
			 * The current hibernateSession will be stored in the httpSession
			 */
			else {
				httpSession.setAttribute(HIBERNATE_SESSION_KEY, sf
						.getCurrentSession());
			}
		} catch (Exception e) {
			invalidateOnError(e, response);
		}
	}

	/**
	 * Opens new session using the session factory and configures it.
	 */
	private void createHibernateSession() {
		hibernateSession = sf.openSession();
		hibernateSession.setFlushMode(FlushMode.MANUAL);
		logger
				.debug("HibernateWebFilter | New HibernateSession has been created | (http session: "
						+ httpSession.getId() + ")");
		logger.trace(httpSession.getId() + " >>> New conversation ");
	}

	/**
	 * Closes the hibernate session and deletes it from the http session
	 */
	private void closeHibernateSession() {
		try {
			if (!sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().close(); // Unbind is automatic here
			}
			httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);
			httpSession.removeAttribute(END_OF_CONVERSATION_FLAG);
			logger
					.trace("HibernateWebFilter | Hibernate session closed (http session: "
							+ httpSession.getId() + ")");
		} catch (HibernateException e) {
			invalidateOnError(e, response);
		}
	}

	/**
	 * In case of an error (an uncatched exception in the system) call this
	 * method to log it and to log out the user.
	 * 
	 * @param e
	 */
	private void invalidateOnError(Exception e, HttpServletResponse response) {
		/*
		 * These exceptions hasn't been catch in the system logic so they would
		 * be shown by the servlet container or application server. Instead they
		 * will be logged and the user will be logged out
		 */
		logger.error("HibernateWebFilter - error| " + e.getMessage(), e);
		closeHibernateSession();
		/*
		 * Invalidating the httpSession and redirecting the response to the log
		 * in page
		 */
		httpSession.invalidate();
		// TODO we could switch to the icefaces configuration option ...
		// look in the retrospectiva ticket
		try {
			response.sendRedirect("login.jspx");
		} catch (IOException e1) {
			logger.error("HibernateWebFilter - error| " + e.getMessage(), e);
		}
	}

}