package de.randi2.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
import de.randi2.utility.NullKonstanten;

/**
 * Diese Klasse repraesentiert das BENUTZERSERVLET, welches Aktionen an die
 * Benutzerkonto-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id$
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * 
 */
public class BenutzerServlet extends javax.servlet.http.HttpServlet {

    
    /**
     * @author Bronx
     *
     */
    public enum anfrage_id{
	/**
	 * 
	 */
	CLASS_DISPATCHERSERVLET_LOGIN1,
	/**
	 * 
	 */
	CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER
	
    }
    /**
         * Konstruktor.
         * 
         * @see javax.servlet.http.HttpServlet#HttpServlet()
         */
    public BenutzerServlet() {
	super();
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
	String id = (String) request.getAttribute("anfrage_id");
	Logger.getLogger(this.getClass()).debug(id);

	// Login
	if (id.equals(BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_LOGIN1.name())) {
	    this.class_dispatcherservlet_login1(request, response);
	}// if

	// Letzter Schritt Benutzer registrieren
	if (id.equals(BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER.name())) {
	    this.class_dispatcherservlet_benutzer_registrieren_vier(request, response);	   
	}// if 
    }// doPost
    private void class_dispatcherservlet_login1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    BenutzerkontoBean sBenutzer = null;
	    try {

		sBenutzer = new BenutzerkontoBean();
		// Filter setzen
		sBenutzer.setFilter(true);
		sBenutzer.setBenutzername((String) request.getParameter("username"));
		// TODO Zukünftig sollte hier gleich das Passwort überprüft
		// werden
		sBenutzer.setPasswortKlartext((String) request.getParameter("password"));
		Vector<BenutzerkontoBean> gBenutzer = null;

		try {
		    gBenutzer = Benutzerkonto.suchenBenutzer(sBenutzer);
		} catch (DatenbankFehlerException e) {
		    // Interner Fehler, wird nicht an Benutzer weitergegeben
		    Logger.getLogger(this.getClass()).fatal("Fehler bei Benutzerfilterung", e);
		}
		if (gBenutzer.size() == 1) {
		    if (!gBenutzer.get(0).isGesperrt() && new Benutzerkonto(gBenutzer.firstElement()).pruefenPasswort((String) request.getParameter("password"))) {
			    if (gBenutzer.get(0).getRolle().getRollenname() == Rolle.Rollen.STUDIENARZT) {
				request.getRequestDispatcher("/studie_auswaehlen.jsp").forward(request, response);
			    } else if (gBenutzer.get(0).getRolle().getRollenname() == Rolle.Rollen.STUDIENLEITER) {
				request.getRequestDispatcher("/studie_auswaehlen.jsp").forward(request, response);
			    } else if (gBenutzer.get(0).getRolle().getRollenname() == Rolle.Rollen.STATISTIKER) {
				request.getRequestDispatcher("/studie_ansehen.jsp").forward(request, response);
			    } else if (gBenutzer.get(0).getRolle().getRollenname() == Rolle.Rollen.ADMIN) {
				request.getRequestDispatcher("/global_welcome.jsp").forward(request, response);
			    } else if (gBenutzer.get(0).getRolle().getRollenname() == Rolle.Rollen.SYSOP) {
				request.getRequestDispatcher("/global_welcome.jsp").forward(request, response);
			    }
			LogAktion a = new LogAktion("Benutzer hat sich erfolgreich eingeloggt", gBenutzer.firstElement());
			Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
		    }// if
		    else {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, "Loginfehler:<br> Bitte Benutzername und Passwort &uuml;berpr&uuml;fen");
			 request.getRequestDispatcher("/index.jsp").forward(request, response);
			LogAktion a = new LogAktion("Falsches Passwort eingegeben.", sBenutzer);
			Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
		    }
		}// if
		else {
		    request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, "Loginfehler:<br> Bitte Benutzername und Passwort &uuml;berpr&uuml;fen");
		    request.getRequestDispatcher("/index.jsp").forward(request, response);
		    if (gBenutzer.size() == 0) {
			LogAktion a = new LogAktion("Falscher Benutzernamen eingegeben.", sBenutzer);
			Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
		    } else {
			Logger.getLogger(this.getClass()).fatal("Mehr als einen Benutzer fuer '" + sBenutzer.getBenutzername() + "' in der Datenbank gefunden!");
		    }
		}// else

	    } catch (BenutzerkontoException e) {

		// Ungueltiger Benutzername/Passwort

		request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, "Loginfehler");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		LogAktion a = new LogAktion("Ungueltige Benutzername/Passwort Kombination eingegeben.", sBenutzer);
		Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
	    }

	
    }
    private void class_dispatcherservlet_benutzer_registrieren_vier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	 // Alle Attribute des request inititalisieren
	    String fehlernachricht = "";
	    String vorname = request.getParameter("Vorname");
	    String nachname = request.getParameter("Nachname");
	    char geschlecht = NullKonstanten.NULL_CHAR;
	    String passwort = null;
	    String email = request.getParameter("Email");
	    String telefon = request.getParameter("Telefon");
	    String fax = request.getParameter("Fax");
	    String handynummer = request.getParameter("Handy");
	    String institut = request.getParameter("Institut");
	    String zent = request.getParameter("aZentrum");
	    int zentrumID = Integer.parseInt(zent);
	    ZentrumBean zentrum = null;
	    String titel = request.getParameter("Titel");

	    if (titel != null && titel.equals("kein Titel")) {
		titel = null;
	    }

	    // TODO Dirty Fix: Da noch keine Suche nach Zentrumbeans möglich
	    // @Andy: man kann doch ein ZentrumBean einfach erzeugen, dazu
	    // brauchen
	    // wir keine konstante in der Zentrum Klasse. Oder steckt noch
            // was
	    // dahinter? (Lukasz)
	    ZentrumBean sZentrum = new ZentrumBean();
	    sZentrum.setFilter(true);
	    Vector<ZentrumBean> gZentrum = null;
	    try {
		gZentrum = Zentrum.suchenZentrum(sZentrum);
	    } catch (DatenbankFehlerException e2) {
		// TODO Wieder muss an dieser Stelle die Ueberlegung gemacht
		// werden, was für eine Nachricht dem Benutzer angezeigt werden
		// soll.
		e2.printStackTrace();
	    }
	    Iterator<ZentrumBean> itgZentrum = gZentrum.iterator();
	    while (itgZentrum.hasNext()) {
		ZentrumBean aZentrumBean = itgZentrum.next();
		if (aZentrumBean.getId() == zentrumID) {
		    zentrum = aZentrumBean;
		}
	    }
	    // Ende Dirty Fix

	    // Geschlecht abfragen
	    if (request.getParameter("maennlich") != null) {
		geschlecht = 'm';
	    } else if (request.getParameter("weiblich") != null) {
		geschlecht = 'w';
	    }
	    // Wiederholte Passworteingabe prüfen
	    if (request.getParameter("Passwort") != null && request.getParameter("Passwort_wh") != null) {
		if (request.getParameter("Passwort").equals(request.getParameter("Passwort_wh"))) {
		    passwort = request.getParameter("Passwort");
		} else {
		    fehlernachricht += "Passwort und wiederholtes Passwort sind nicht gleich";
		}
	    }
	    // Benutzer anlegen
	    // Hier findet die Überprüfung der Daten auf Serverseite statt,
	    // Fehler wird an Benutzer weiter gegeben
	    PersonBean aPerson = null;
	    try {
		aPerson = new PersonBean(nachname, vorname, titel, geschlecht, email, telefon, handynummer, fax);
		BenutzerkontoBean aBenutzerkonto;
		aBenutzerkonto = new BenutzerkontoBean(email, passwort, aPerson);
		aBenutzerkonto.setZentrum(zentrum);
		Benutzerkonto.anlegenBenutzer(aBenutzerkonto);

		// Hier startet das Abfangen aller Fehlerprüfungen
	    } catch (DatenbankFehlerException e) {
		Logger.getLogger(this.getClass()).warn("Nutzer konnte nicht in die Datenbank aufgenommen werden", e);
		fehlernachricht += e.getMessage() + "\n";
	    } catch (BenutzerkontoException e1) {
		Logger.getLogger(this.getClass()).info("Fehler mit Passwort oder Email", e1);
		fehlernachricht += e1.getMessage() + "\n";
	    } catch (PersonException e) {
		Logger.getLogger(this.getClass()).info("Persondaten ungültig", e);
		fehlernachricht += e.getMessage() + "\n";
		e.printStackTrace();
	    }
	    // Falls ein Fehler aufgetreten ist, request wieder auffüllen
	    if (fehlernachricht != "") {

		request.setAttribute("Vorname", vorname);
		request.setAttribute("Nachname", nachname);
		if (geschlecht == 'm') {
		    request.setAttribute("maennlich", "maennlich");
		} else if (geschlecht == 'w') {
		    request.setAttribute("weiblich", "weiblich");
		}
		request.setAttribute("Titel", titel);
		request.setAttribute("Passwort", request.getParameter("Passwort"));
		request.setAttribute("Passwort_wh", request.getParameter("Passwort_wh"));
		request.setAttribute("Email", email);
		request.setAttribute("Telefon", telefon);
		request.setAttribute("Fax", fax);
		request.setAttribute("aZentrum", zentrum.getId());
		request.setAttribute("Handy", handynummer);
		request.setAttribute("Institut", institut);

		// Ausgeben der Fehlermeldung
		request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, fehlernachricht);
		request.getRequestDispatcher("/benutzer_anlegen_drei.jsp").forward(request, response);
	    } else {
		request.getRequestDispatcher("/benutzer_anlegen_vier.jsp").forward(request, response);
	    }// else
	
    }
}
