package de.randi2.controller.listener;

import java.util.GregorianCalendar;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.DBObjekt;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.SystemException;

/**
 * Der SessionListener realisiert einen BenutzerCounter.<br>
 * Wird das Benutzerkontobean des Benutzers an die Session gebunden, er wird
 * also eingeloggt, so erhoeht sich der Counter um 1. wird der Benutzer
 * ausgeloggt, das Bean wird also wieder aus der Session entfernt, wird der
 * Counter wieder verringert.
 * 
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * @version $Id$
 */
public class SessionListener implements HttpSessionAttributeListener, HttpSessionListener {

	/**
	 * Anzahl der im System angemeldeten Benutzer
	 */
	private static int counter = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 */
	/**
	 * Wird ein {@link BenutzerkontoBean} unter dem Namen 'aBenutzer' (ein
	 * Benutzer loggt sich am system ein) an die Session gebunden, wird der
	 * Counter um 1 erhoeht.
	 * 
	 * @param be
	 *            Durch das Binding automatisch ausgeloeste Event
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent be) {
		if (be.getValue() instanceof BenutzerkontoBean && be.getName().equals("aBenutzer")) {
			erhoeheCounter();
		}
	}

	/**
	 * Wird das BenutzerkontoBean 'aBenutzer' aus der Session entfernt (der
	 * Benutzer wird ausgeloggt), so wird der BenutzerCounter um 1 erniedriegt.
	 * 
	 * @param be
	 *            Durch das Entfernen des Attributes automatisch ausgeloeste
	 *            Event
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent be) {
		if (be.getValue() instanceof BenutzerkontoBean && be.getName().equals("aBenutzer")) {
			erniedrigeCounter();
		}

	}

	/**
	 * Wird aufgerufen, wenn ein Attribut, welches an einer Session gebunden
	 * ist, ersetzt wird.
	 * 
	 * @param be
	 *            ausgeloestes Event
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent be) {

	}

	/**
	 * Erhoeht den Benutzercounter um 1
	 */
	private synchronized void erhoeheCounter() {
		counter++;
	}

	/**
	 * Reduziert den Benutzercounter um 1
	 */
	private synchronized void erniedrigeCounter() {
		counter--;
	}

	/**
	 * Liefert die Anzahl der aktuell angemeldetet Benutzer am System
	 * 
	 * @return Anzahl der angemeldeten Benutzer
	 */
	public static synchronized int getCounter() {
		return counter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		Logger.getLogger(this.getClass()).debug("Session wurde erzeugt");
	}

	/**
	 * Wenn die Session verstoert wird und ein Benutzer an die Session gebunden
	 * ist, so wird die
	 * {@link BenutzerkontoBean#setLetzterLogin(GregorianCalendar)} mit der
	 * aktuellen Zeit gespeichert und in der Db gespeichert
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) arg0.getSession().getAttribute("aBenutzer");
		if (aBenutzer != null) {
			try {
				aBenutzer.setLetzterLogin(new GregorianCalendar());
				aBenutzer.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
				DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aBenutzer);
				Logger.getLogger(this.getClass()).debug("Aktualisiere 'LetzterLogin' fuer Konto "+aBenutzer.getBenutzername());
			} catch (Exception e) {
				new SystemException("Setzten des letzten Logins fehlgeschlagen");
			}
		}
		Logger.getLogger(this.getClass()).debug("Session wurde zerstoert");
	}
}
