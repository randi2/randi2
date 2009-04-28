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

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.context.ManagedSessionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HibernateWebFilter implements Filter {

	private SessionFactory sf;

    public static final String HIBERNATE_SESSION_KEY    = "hibernateSession";
    public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {


        // Try to get a Hibernate Session from the HttpSession
        HttpSession httpSession =
                ((HttpServletRequest) request).getSession();
        Session hibernateSession =
                (Session) httpSession.getAttribute(HIBERNATE_SESSION_KEY);
        System.out.println(httpSession.getId());

        try {

            if (hibernateSession != null) {
              
               if(!hibernateSession.isOpen()){
            	   hibernateSession = sf.openSession();
            	   hibernateSession.setFlushMode(FlushMode.MANUAL);
            	   //System.out.println(httpSession.getId() + " >>> New conversation " + hibernateSession.toString());
               }//else{
//            	   //System.out.println(httpSession.getId() + " < Continuing conversation ");
//               }
               hibernateSession.setFlushMode(FlushMode.MANUAL);
               ManagedSessionContext.bind((org.hibernate.classic.Session)hibernateSession);
            } else {
            	 
               hibernateSession = sf.openSession();
               //System.out.println(httpSession.getId() + " >>> New conversation "+ hibernateSession.toString());
               hibernateSession.setFlushMode(FlushMode.MANUAL);
               ManagedSessionContext.bind((org.hibernate.classic.Session) hibernateSession);
            }

            // Do the work...
            chain.doFilter(request, response);

            // End or continue the long-running conversation?
            if (request.getAttribute(END_OF_CONVERSATION_FLAG) != null ||
                request.getParameter(END_OF_CONVERSATION_FLAG) != null) {

                sf.getCurrentSession().flush();
                sf.getCurrentSession().close(); // Unbind is automatic here
               //System.out.println("Removing Session from HttpSession");
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);
               //System.out.println(httpSession.getId() + " <<< End of conversation");

            } else {

            	try{
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, hibernateSession);
                hibernateSession = ManagedSessionContext.unbind(sf);
                //System.out.println(httpSession.getId() + " > Returning to user in conversation");
              }catch (IllegalStateException e) {
                   sf.getCurrentSession().flush();
                   sf.getCurrentSession().close(); // Unbind is automatic here
                   //System.out.println("<<< End of conversation " + httpSession.getId());
              }
            }

        } catch (StaleObjectStateException staleEx) {
            //System.out.println("This interceptor does not implement optimistic concurrency control!");
            //System.out.println("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
        	ex.printStackTrace();
            // Rollback only
            try {
                if (sf.getCurrentSession().getTransaction().isActive()) {
                   //System.out.println("Trying to rollback database transaction after exception");
                    sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
            	rbEx.printStackTrace();
                //System.out.println("Could not rollback transaction after exception!");//), rbEx);
            } finally {
                ////System.out.println("Cleanup after exception!");

                // Cleanup
               //System.out.println("Closing and unbinding Session from thread");
                sf.getCurrentSession().close(); // Unbind is automatic here

               //System.out.println("Removing Session from HttpSession");
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);

            }

            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
        
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    	sf = (SessionFactory)wac.getBean("sessionFactory");
    	
    }

    public void destroy() {}


}
