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
	 * Identifier for the hibernateSession within the httpSession
	 */
	public static final String HIBERNATE_SESSION_KEY = "hibernateSession";
	/**
	 * Use this attribute to end the conversation and detach the hibernate
	 * session
	 */
	public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";

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
		this.response = (HttpServletResponse) response;
		logger.trace("HibernateWebFilter | " + httpSession.getId());
		/*
		 * Go and do the work ...
		 */
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			invalidateOnError(e, this.response);
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
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