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
 * @author Lukasz Plotnicki <lp@randi2.de>
 * 
 */
public class HibernateWebFilter implements Filter {

	private Logger logger = Logger.getLogger(HibernateWebFilter.class);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		httpSession = ((HttpServletRequest) request).getSession();
		hibernateSession = (Session) httpSession
				.getAttribute(HIBERNATE_SESSION_KEY);
		logger.trace(httpSession.getId());
		beforeLogic();
		/*
		 * Go and do the work ...
		 */
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("EEEEXCEPTION", e); // TODO Log it properly
			closeSession();
			httpSession.invalidate(); // TODO It would be nice to redirect
			((HttpServletResponse)response).sendRedirect("login.jspx");
			return;
		}
		afterLogic();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		sf = (SessionFactory) wac.getBean("sessionFactory");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * This method contains the logic which will be executed after forrwording
	 * the request to its destination. It tries to find the exisiting
	 * hibernateSession if attached to the httpSession - in other case it
	 * creates a new one.
	 */
	private void beforeLogic() {
		/*
		 * A hibernateSession has been found in the current httpSession
		 */
		if (hibernateSession != null) {
			/*
			 * The found session is closed - a new will be opened - the
			 * conversation begins
			 */
			if (!hibernateSession.isOpen()) {
				createSession();
				logger.debug("open Hibernate session (http session: "
						+ httpSession.getId() + ")");
				logger.trace(httpSession.getId() + " >>> New conversation ");
				/*
				 * Continue the conversation
				 */
			} else {
				logger.trace(httpSession.getId()
						+ " < Continuing conversation ");
			}
		}
		/*
		 * If the httpSession doesn't contain the hibernateSession a new one
		 * will be created
		 */
		else {
			createSession();
			logger.trace(httpSession.getId() + " >>> New conversation");
			logger.debug("open Hibernate session (http session: "
					+ httpSession.getId() + ")");

		}
		/*
		 * Binds the hibernateSession
		 */
		ManagedSessionContext
				.bind((org.hibernate.classic.Session) hibernateSession);
	}

	/**
	 * Checks if the conversation is over - if so the hibernateSession will be
	 * closed and flushed and also detached from the httpSession. In other case
	 * it will be reattached to the httpSession.
	 */
	private void afterLogic() {
		/*
		 * End or continue the long-running conversation?
		 */
		try {
			/*
			 * END_OF_CONVERSATION_FLAG found - the hibernate session will be
			 * detached
			 */
			if (httpSession.getAttribute(END_OF_CONVERSATION_FLAG) != null) {
				closeSession();
			}
			/*
			 * The current hibernateSession will be stored in the httpSession
			 */
			else {
				httpSession.setAttribute(HIBERNATE_SESSION_KEY, sf
						.getCurrentSession());
			}
		} catch (Exception e) {
			if (e instanceof IllegalStateException) {
				logger.trace("Trace Exception", e);
				if (sf.getCurrentSession().isOpen()) {
					closeSession();
				}
			} else {
				logger.error("EEEEXCEPTION", e); // TODO Log it properly
				closeSession();
				httpSession.invalidate(); // TODO It would be nice to redirect
				// the user to an error or loging
				// page with an error message
			}
		}
	}

	/**
	 * Opens new session using the session factory and configures it.
	 */
	private void createSession() {
		hibernateSession = sf.openSession();
		hibernateSession.setFlushMode(FlushMode.MANUAL);
	}

	/**
	 * Closes the hibernate session and deletes it from the http session
	 */
	private void closeSession() {
		try {
			if (!sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().flush();
				sf.getCurrentSession().close(); // Unbind is automatic here
			}
			httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);
			httpSession.removeAttribute(END_OF_CONVERSATION_FLAG);
			logger.debug("Hibernate session closed (http session: "
					+ httpSession.getId() + ")");
		} catch (HibernateException e) {
			logger.error("dough!", e); // TODO Log it properly
		}
	}

}
