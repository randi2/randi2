package de.randi2.utility.webfilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HibernateWebFilter implements Filter {

	private Logger logger = Logger.getLogger(HibernateWebFilter.class);
	private SessionFactory sf;

	public static final String HIBERNATE_SESSION_KEY = "hibernateSession";
	public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Try to get a Hibernate Session from the HttpSession
		HttpSession httpSession = ((HttpServletRequest) request).getSession();
		Session hibernateSession = (Session) httpSession
				.getAttribute(HIBERNATE_SESSION_KEY);
		logger.trace(httpSession.getId());
//		try {
			if (hibernateSession != null) {
				if (!hibernateSession.isOpen()) {
					hibernateSession = sf.openSession();
					logger.debug("open Hibernate session (http session: " +httpSession.getId() + ")");
					logger
							.trace(httpSession.getId()
									+ " >>> New conversation ");
				} else {
					logger.trace(httpSession.getId()
							+ " < Continuing conversation ");
				}
			} else {
				hibernateSession = sf.openSession();
				logger.trace(httpSession.getId() + " >>> New conversation");
				logger.debug("open Hibernate session (http session: " +httpSession.getId() + ")");
				
			}
			hibernateSession.setFlushMode(FlushMode.COMMIT);
			ManagedSessionContext
					.bind((org.hibernate.classic.Session) hibernateSession);
			// Do the work...
			chain.doFilter(request, response);
			// End or continue the long-running conversation?
			try {
				if (httpSession.getAttribute(END_OF_CONVERSATION_FLAG) != null) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().close(); // Unbind is automatic here
					httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);
					//SecurityContextHolder.getContext().setAuthentication(null);
					httpSession.removeAttribute(END_OF_CONVERSATION_FLAG);
					logger.debug("Hibernate session closed (http session: " +httpSession.getId() + ")");
				}else{
					httpSession.setAttribute(HIBERNATE_SESSION_KEY, sf.getCurrentSession());
				}
			} catch (IllegalStateException e) {
				logger.trace("Trace Exception", e);
				if(sf.getCurrentSession().isOpen()){
				sf.getCurrentSession().flush();
				sf.getCurrentSession().close(); // Unbind is automatic here
				logger.debug("Hibernate session closed (http session: " +httpSession.getId() + ")");
				}
				//SecurityContextHolder.getContext().setAuthentication(null);
			}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		sf = (SessionFactory) wac.getBean("sessionFactory");

	}

	public void destroy() {
	}

}
