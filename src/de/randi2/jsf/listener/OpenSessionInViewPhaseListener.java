package de.randi2.jsf.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class OpenSessionInViewPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4750692394367117452L;

	public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";

	private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

	private boolean singleSession = true;
	
	/**
	 * Holds the thread data for the current request cycle.
	 */
	private static ThreadLocal<ThreadData> ts = new ThreadLocal<ThreadData>();
	
	/** This class is a bit of hack
	 * In the OpenSessionInViewFilter, the filtering is accomplished in one method.
	 * This is not an option with this approach.
	 * This class holds all of the local variables that were in the original doFilter method
	 * of OpenSessionInViewFilter.
	 * @author Rick
	 *
	 */
	private class ThreadData {
		private boolean participate;
		private Session session;
		private SessionFactory sessionFactory;
		private int beforeTimes;
		private int afterTimes;
	}
	
	/** @see javax.faces.event.PhaseListener(javax.faces.event.PhaseEvent pe)
	 * 
	 */
	public void beforePhase(PhaseEvent pe) {
		
		/* Setup the Hiberante Spring in the current thread using Spring API */
		if (pe.getPhaseId() == PhaseId.RESTORE_VIEW) {
			ThreadData td = getThreadData();
			/* This code is here b/c MyFaces seems to call every phase handler method twice. */
			if (td.beforeTimes==0) {
				System.out.println("BEFOREPHASE");
				setupSession(pe, td);
			}
		} /* HACK ALERT!
		     The before restore_view does not get called for Apache JSF Portlet 
		     bridge when first viewing the JSF Portlet.
		 	 Thus we will do this just before the render_response phase.*/ 
		else if (pe.getPhaseId() == PhaseId.RENDER_RESPONSE){
				
				ThreadData td = getThreadDataNoCreate();
				if (td==null){
					td = getThreadData();
					setupSession(pe, td);					
				}
			
		}
	}
	
	/** Get the current thread data */
	private ThreadData getThreadData() {
		ThreadData td = (ThreadData) ts.get();
		if (td == null) {
			td = new ThreadData();
			ts.set(td);
		} else {
			td.beforeTimes++;
		}
		return td;

	}

	/** Get the thread data don't create it if it does not exist */
	private ThreadData getThreadDataNoCreate() {
		ThreadData td = (ThreadData) ts.get();
		return td;

	}
	
	/* Setup the Hiberante session. This was taken from the first half of the doFilter method. */
	private void setupSession(PhaseEvent pe, ThreadData td) {
	
		td.sessionFactory = lookupSessionFactory(pe.getFacesContext());
		Assert.notNull(td.sessionFactory);
		td.session = null;
		td.participate = false;

		if (isSingleSession()) {
			// single session mode
			if (TransactionSynchronizationManager.hasResource(td.sessionFactory)) {
				// Do not modify the Session: just set the participate flag.
				td.participate = true;
			}
			else {
				td.session = getSession(td.sessionFactory);
				TransactionSynchronizationManager.bindResource(td.sessionFactory, new SessionHolder(td.session));
			}
		}
		else {
			// deferred close mode
			if (SessionFactoryUtils.isDeferredCloseActive(td.sessionFactory)) {
				// Do not modify deferred close: just set the participate flag.
				td.participate = true;
			}
			else {
				SessionFactoryUtils.initDeferredClose(td.sessionFactory);
			}
		}
	}

	/** @see javax.faces.event.PhaseListener(javax.faces.event.PhaseEvent pe)
	 * 
	 */	
	public void afterPhase(PhaseEvent pe) {	

//		
//		if (pe.getPhaseId() == PhaseId.RENDER_RESPONSE) {
//			System.out.println("AfterPHASE");
//			ThreadData td = getThreadDataNoCreate();
//			if (td==null) return;
//			td.afterTimes++;
//			cleanupSession(td);
//			
//			ts.set(null);
//		}
	}

	/* Cleanup the Hiberante session. This was taken from the first half of the doFilter method. */
	private void cleanupSession(ThreadData td) {
		if (!td.participate) {
			if (isSingleSession()) {
				// single session mode					
				try {
					TransactionSynchronizationManager.unbindResource(td.sessionFactory);					
					closeSession(td.session, td.sessionFactory);
				}
				catch (RuntimeException ex) {
					ex.printStackTrace();
				}
			}
			else {
				// deferred close mode
				SessionFactoryUtils.processDeferredClose(td.sessionFactory);
			}
		}
	}


	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
		this.sessionFactoryBeanName = sessionFactoryBeanName;
	}

	protected String getSessionFactoryBeanName() {
		return sessionFactoryBeanName;
	}

	/**
	 * Set whether to use a single session for each request. Default is "true".
	 * <p>If set to false, each data access operation or transaction will use
	 * its own session (like without Open Session in View). Each of those
	 * sessions will be registered for deferred close, though, actually
	 * processed at request completion.
	 * @see SessionFactoryUtils#initDeferredClose
	 * @see SessionFactoryUtils#processDeferredClose
	 */
	public void setSingleSession(boolean singleSession) {
		this.singleSession = singleSession;
	}

	/**
	 * Return whether to use a single session for each request.
	 */
	protected boolean isSingleSession() {
		return singleSession;
	}




	/**
	 * Look up the SessionFactory that this filter should use.
	 * <p>Default implementation looks for a bean with the specified name
	 * in Spring's root application context.
	 * @return the SessionFactory to use
	 * @see #getSessionFactoryBeanName
	 */
	protected SessionFactory lookupSessionFactory(FacesContext facesContext) {		
		WebApplicationContext wac = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
		return (SessionFactory) wac.getBean(getSessionFactoryBeanName(), SessionFactory.class);
	}

	/**
	 * Get a Session for the SessionFactory that this filter uses.
	 * Note that this just applies in single session mode!
	 * <p>The default implementation delegates to SessionFactoryUtils'
	 * getSession method and sets the Session's flushMode to NEVER.
	 * <p>Can be overridden in subclasses for creating a Session with a custom
	 * entity interceptor or JDBC exception translator.
	 * @param sessionFactory the SessionFactory that this filter uses
	 * @return the Session to use
	 * @throws DataAccessResourceFailureException if the Session could not be created
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory, boolean)
	 * @see org.hibernate.FlushMode#NEVER
	 */
	protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		//session.setFlushMode(FlushMode.MANUAL);
		return session;
	}

	/**
	 * Close the given Session.
	 * Note that this just applies in single session mode!
	 * <p>The default implementation delegates to SessionFactoryUtils'
	 * releaseSession method.
	 * <p>Can be overridden in subclasses, e.g. for flushing the Session before
	 * closing it. See class-level javadoc for a discussion of flush handling.
	 * Note that you should also override getSession accordingly, to set
	 * the flush mode to something else than NEVER.
	 * @param session the Session used for filtering
	 * @param sessionFactory the SessionFactory that this filter uses
	 */
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}	
}
