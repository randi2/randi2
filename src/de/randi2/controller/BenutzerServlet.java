package de.randi2.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.*;
import de.randi2.model.exceptions.*;

/**
 * Diese Klasse repraesentiert das BENUTZERSERVLET, welches Aktionen an die
 * Benutzerkonto-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id$
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
 * @author Andreas Freudling <afreudling@stud.hs-heilbronn.de>
 * 
 */
public class BenutzerServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public BenutzerServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Hier wird je nach Aktion unterschieden, was passiert.
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = (String) request.getAttribute("anfrage_id");
		Logger.getLogger(this.getClass()).debug(id);

		// Login
		if (id.equals("CLASS_DISPATCHERSERVLET_LOGIN1")) {
			BenutzerkontoBean sBenutzer = null;
			try {

				sBenutzer = new BenutzerkontoBean();

				sBenutzer.setFilter(true);
				sBenutzer.setBenutzername((String) request
						.getParameter("username"));
				// Zukünftig sollte hier gleich das Passwort überprüft werden
				sBenutzer.setPasswort(PasswortUtil.getInstance().hashPasswort(
						(String) request.getParameter("password")));
				// TODO Lukasz: Ich habe den try/catch Block eingeführt, damit
				// die Selenium Test gemacht werden können. Ansonsten kam eine
				// fette Exception beim Tomcat.
				Vector<BenutzerkontoBean> gBenutzer = null;
				// TODO Ich weiß auch nich warum - aber es wird beim dem
				// sBenutzer Objekt nicht setFilter() aufgerufen. Deswegen mache
				// ich das an dieser Stelle:
				try {
					gBenutzer = Benutzerkonto.suchenBenutzer(sBenutzer);
				} catch (DatenbankFehlerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (gBenutzer.size() == 1) {
					if (!gBenutzer.get(0).isGesperrt()
							&& new Benutzerkonto(gBenutzer.firstElement())
									.pruefenPasswort((String) request
											.getParameter("password"))) {
						request.getSession(true).setAttribute("aBenutzer",
								gBenutzer.firstElement());
						request.setAttribute("anfrage_id",
								"CLASS_BENUTZERSERVLET_LOGIN_OK");
						LogAktion a = new LogAktion(
								"Benutzer hat sich erfolgreich eingeloggt",
								gBenutzer.firstElement());
						Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
					}// if
					else {
						request.setAttribute("fehlernachricht", "Loginfehler");
						request.setAttribute("anfrage_id",
								"CLASS_BENUTZERSERVLET_LOGIN_ERROR");
						LogAktion a = new LogAktion(
								"Falsches Passwort eingegeben.", sBenutzer);
						Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
					}
				}// if
				else {
					request.setAttribute("fehlernachricht", "Loginfehler");
					request.setAttribute("anfrage_id",
							"CLASS_BENUTZERSERVLET_LOGIN_ERROR");
					if (gBenutzer.size() == 0) {
						LogAktion a = new LogAktion(
								"Falscher Benutzernamen eingegeben.", sBenutzer);
						Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
					} else {
						Logger.getLogger(this.getClass()).fatal(
								"Mehr als einen Benutzer fuer '"
										+ sBenutzer.getBenutzername()
										+ "' in der Datenbank gefunden!");
					}
				}// else

			} catch (BenutzerkontoException e) {

				// Ungueltiger Benutzername/Passwort

				request.setAttribute("fehlernachricht", "Loginfehler");
				request.setAttribute("anfrage_id",
						"CLASS_BENUTZERSERVLET_LOGIN_ERROR");
				LogAktion a = new LogAktion(
						"Ungueltige Benutzername/Passwort Kombination eingegeben.",
						sBenutzer);
				Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
			}

			request.getRequestDispatcher("DispatcherServlet").forward(request,
					response);
		}// if

		// Letzter Schritt Benutzer registrieren
		if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER")) {

			String fehlernachricht = "";
			String vorname = request.getParameter("Vorname");
			String nachname = request.getParameter("Nachname");
			char geschlecht = NullKonstanten.NULL_CHAR;
			String passwort = null;
			String email = request.getParameter("Email");
			String telefon = request.getParameter("Telefon");
			String fax = request.getParameter("Fax");
			// TODO dirty fix
			String handynummer = request.getParameter("Handy");
			String institut = request.getParameter("Institut");
			String zent = request.getParameter("aZentrum");
			int zentrumID = Integer.parseInt(zent);
			ZentrumBean zentrum = null;
			String titel = request.getParameter("Titel");
			try {
				if (titel != null && titel.equals("kein Titel")) {
					titel = null;
				}

				// Dirty Fix: Da noch keine Suche nach Zentrumbeans möglich
				ZentrumBean sZentrum=Zentrum.NULL_ZENTRUM;
				sZentrum.setFilter(true);
				Vector<ZentrumBean> gZentrum = Zentrum.suchenZentrum(sZentrum);
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
				if (request.getParameter("Passwort") != null
						&& request.getParameter("Passwort_wh") != null) {
					if (request.getParameter("Passwort").equals(
							request.getParameter("Passwort_wh"))) {
						passwort = request.getParameter("Passwort");
					} else {
						fehlernachricht += "Passwort und wiederholtes Passwort sind nicht gleich";
					}
				}
				// TODO Lukasz: Ich habe den try/catch Block eingeführt, damit
				// die Selenium Test gemacht werden können. Ansonsten kam eine
				// fette Exception beim Tomcat.
				PersonBean aPerson = null;
				try {
					aPerson = new PersonBean(nachname, vorname, titel,
							geschlecht, email, telefon, handynummer, fax);
				} catch (PersonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BenutzerkontoBean aBenutzerkonto = new BenutzerkontoBean(email,
						passwort, aPerson);
				aBenutzerkonto.setZentrum(zentrum);
				// TODO Lukasz: Ich habe den try/catch Block eingeführt, damit
				// die Selenium Test gemacht werden können. Ansonsten kam eine
				// fette Exception beim Tomcat.
				try {
					Benutzerkonto.anlegenBenutzer(aBenutzerkonto);
				} catch (DatenbankFehlerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (BenutzerkontoException e) {
				fehlernachricht += e.getMessage();
			}
			// Daten waren nicht fehlerfrei
			if (fehlernachricht != "") {

				request.setAttribute("Vorname", vorname);
				request.setAttribute("Nachname", nachname);
				if (geschlecht == 'm') {
					request.setAttribute("maennlich", "maennlich");
				} else if (geschlecht == 'w')
					request.setAttribute("weiblich", "weiblich");
				request.setAttribute("Titel", titel);
				request.setAttribute("Passwort", request
						.getParameter("Passwort"));
				request.setAttribute("Passwort_wh", request
						.getParameter("Passwort_wh"));
				request.setAttribute("Email", email);
				request.setAttribute("Telefon", telefon);
				request.setAttribute("Fax", fax);
				request.setAttribute("aZentrum", zentrum.getId());
				request.setAttribute("Handy", handynummer);
				request.setAttribute("Institut", institut);

				request.setAttribute("fehlernachricht", fehlernachricht);
				request.getRequestDispatcher("/benutzer_anlegen_drei.jsp")
						.forward(request, response);
			} else {
				request.getRequestDispatcher("/benutzer_anlegen_vier.jsp")
						.forward(request, response);
			}
		}

	}// doPost

}