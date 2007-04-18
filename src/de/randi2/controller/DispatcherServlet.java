package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.Config;
/**
 * <p>
 * Diese Klasse repraesentiert den DISPATCHER (== Weiterleiter). Dieser wird von
 * jeder Anfrage im Projekt angesprochen und leitet diese dann an die
 * entsprechenden Unterservlets bzw. direkt an JSPs weiter.
 * </p>
 * 
 * @version $Id$
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * 
 */
public class DispatcherServlet extends javax.servlet.http.HttpServlet {

    
    /**
     * 
     */
    private boolean istSystemGesperrt=false;
    private String systemsperrungFehlermeldung="";
    /**
     * 
     */
    public static final String FEHLERNACHRICHT="fehlernachricht";
    /**
         * Konstruktor.
         * 
         * @see javax.servlet.http.HttpServlet#HttpServlet()
         */
    public DispatcherServlet() {
	super();
//	Einlesen der Systemsperrung
	//istSystemGesperrt=Boolean.getBoolean(Config.getProperty(Config.Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG));
	//systemsperrungFehlermeldung=Config.getProperty(Config.Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
    }
    
    /**
     * 
     *
     */
    public enum anfrage_id
    {
	/**
	 * 
	 */
	JSP_HEADER_LOGOUT,
	/**
	 * 
	 */
	JSP_INDEX_LOGIN,
	/**
	 * 
	 */
	JSP_INDEX_BENUTZER_REGISTRIEREN_EINS,
	/**
	 * 
	 */
	JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI,
	/**
	 * 
	 */
	JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI,
	/**
	 * 
	 */
	JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER
    }

    // TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
    /**
         * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
         * entgegen.
         * 
         * @param request
         *                Der Request fuer das Servlet.
         * @param response
         *                Der Response Servlet.
         * @throws IOException
         *                 Falls Fehler in den E/A-Verarbeitung.
         * @throws ServletException
         *                 Falls Fehler in der HTTP-Verarbeitung auftreten.
         * 
         * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest
         *      request, HttpServletResponse response)
         */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String id = (String) request.getParameter("anfrage_id");

	// logout (wirklich an dieser Stelle?? oder in BenutezrServelet)
	if (id.equals(DispatcherServlet.anfrage_id.JSP_HEADER_LOGOUT)) {

	    request.getSession().invalidate();
	    request.getRequestDispatcher("index.jsp").forward(request, response);

	}

    }

    // TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
    /**
         * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
         * entgegen.
         * 
         * @param request
         *                Der Request fuer das Servlet.
         * @param response
         *                Der Response Servlet.
         * @throws IOException
         *                 Falls Fehler in den E/A-Verarbeitung.
         * @throws ServletException
         *                 Falls Fehler in der HTTP-Verarbeitung auftreten.
         * 
         * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest
         *      request, HttpServletResponse response)
         */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String id = (String) request.getParameter("anfrage_id");
	String idAttribute = (String) request.getAttribute("anfrage_id");
	if (idAttribute != null) {
	    id = idAttribute;
	}
	Logger.getLogger(this.getClass()).debug(id);
	
	
	//WEITERLEITUNGEN FUER BENUTZERSERVLET
	// [start]
	// Login
	if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name())) {
	    request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
	    request.getRequestDispatcher("BenutzerServlet").forward(request, response);
	} 

	// Benutzer registrieren
	// Schritt 1.1: STARTSEITE->DISCLAIMER
	else if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS.name())) {

	    request.getRequestDispatcher("/benutzer_anlegen_eins.jsp").forward(request, response);

	}
	// Schritt 2.1:DISCLAIMER->ZENTRUMAUSWAHL
	else if (id.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI.name())) {

	    request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
	    request.getRequestDispatcher("ZentrumServlet").forward(request, response);
	}

	// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
	// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
	else if (id.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI.name())) {
	    request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI");
	    request.getRequestDispatcher("ZentrumServlet").forward(request, response);

	}

	// Schritt 4: BENUTZERDATEN_EINGEBEN->
	else if (id.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER.name())) {
	    request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER");
	    request.getRequestDispatcher("BenutzerServlet").forward(request, response);

	}
	// [end]
	//WEITERLEITUNGEN FUER ZENTRUMSERVLET
	// [start]
	// [end]

	//SONSTIGE WEITERLEITUNGEN
	//Schwerer Fehler: Falscher Request
	else{
	    System.out.println("Scheiße");
	    //TODO Hier muss noch entschieden werden,was passiert
	}

    }// doPost

    /**
     * @return the istSystemGesperrt
     */
    public boolean isIstSystemGesperrt() {
        return istSystemGesperrt;
    }

    /**
     * @param istSystemGesperrt the istSystemGesperrt to set
     */
    public void setIstSystemGesperrt(boolean istSystemGesperrt) {
        this.istSystemGesperrt = istSystemGesperrt;
    }

    /**
     * @return the systemsperrungFehlermeldung
     */
    public String getSystemsperrungFehlermeldung() {
        return systemsperrungFehlermeldung;
    }

    /**
     * @param systemsperrungFehlermeldung the systemsperrungFehlermeldung to set
     */
    public void setSystemsperrungFehlermeldung(String systemsperrungFehlermeldung) {
        this.systemsperrungFehlermeldung = systemsperrungFehlermeldung;
    }
}// DispatcherServlet
