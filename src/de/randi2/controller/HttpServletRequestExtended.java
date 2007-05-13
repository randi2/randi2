package de.randi2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

/**
 * Diese Klasse realisiert alle Aenderungen an Standard Request Methoden. 
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @version $Id: HttpServletRequestExtended.java 2375 2007-05-04 07:26:15Z afreudli $
 *
 */
public final class HttpServletRequestExtended extends HttpServletRequestWrapper {
    
    /**
     * Konstruktor zur Weiterleitung des Request Objektes an den HttpServletRequestWrapper
     * @param servletRequest das Standard Request Interface
     */
    public HttpServletRequestExtended(HttpServletRequest servletRequest) {
	super(servletRequest);
    }
    
    /** Erweitert die Methode getParameterValues(String), dahingehend, dass alle Eingaben getrimmt werden
     * @param parameter Name der Parameter
     * @return die getrimmten Strings
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
     */
    @Override
    public String[] getParameterValues(String parameter) {
	String[] results = super.getParameterValues(parameter);
	if (results == null){
	    return null; 
	}

	int count = results.length;

	String[] trimResults = new String[count];
	for (int i = 0; i < count; i++) {
	    trimResults[i] = results[i].trim();
	}
	return trimResults;
    }

    
    /**
     * Erweitert die Methode getParameter(String), dahingehend, dass alle Parameter getrimmt werden und zusätzlich falls Sie einen leeren String enthalten auf null gesetzt werden.
     * @param param Name des Parameters
     * @return Den getrimmten bzw. auf null gesetzten String
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String param) {
	Logger.getLogger(this.getClass()).info("Get Parameter wurde benutzt");
	String rueckgabewert = super.getParameter(param);
	if (rueckgabewert == null) {
	    return null;
	}
	rueckgabewert=rueckgabewert.trim();
	if (rueckgabewert.equals("")) {
	    rueckgabewert = null;
	}
	return rueckgabewert;
    }
}
