package de.randi2.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.*;
import de.randi2.model.exceptions.*;

/**
 * Diese Klasse repraesentiert das BENUTZERSERVLET, welches Aktionen an die
 * Benutzerkonto-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id$
 * @author Daniel Haehn <dhaehn@stud.hs-heilbronn.de>
 * @author Lukasz Plotnicki <lplotni@stud.hs-heilbronn.de>
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
			BenutzerkontoBean sBenutzer = new BenutzerkontoBean();
			try {
				sBenutzer.setBenutzername((String) request
						.getParameter("username"));
				// Zukünftig sollte hier gleich das Passwort überprüft werden
				sBenutzer.setPasswort(PasswortUtil.getInstance().hashPasswort(
						(String) request.getParameter("password")));

				Vector<BenutzerkontoBean> gBenutzer = Benutzerkonto
						.suchenBenutzer(sBenutzer);
				System.out.println("achtung" + gBenutzer.size());
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

			} catch (IllegalArgumentException e) {
				
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

		// // Benutzer registrieren
		// // Schritt 1:
		// else if (id
		// .equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI")) {
		//
		// request.setAttribute("anfrage_id",
		// "CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
		// // Hier noch jede Menge Logik
		// request.getRequestDispatcher("DispatcherServlet").forward(request,
		// response);
		// }
		// // Schritt 2:
		// else if (id
		// .equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI")) {
		// request.setAttribute("anfrage_id",
		// "CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_DREI");
		// // Hier noch jede Menge Logik
		// request.getRequestDispatcher("DispatcherServlet").forward(request,
		// response);
		// request.getRequestDispatcher("/benutzer_anlegen_drei.jsp").forward(request,
		// response);
		//
		// }
		// // Schritt 3:
		// else if (id
		// .equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER")) {
		// request.setAttribute("anfrage_id",
		// "CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_VIER");
		// // Hier noch jede Menge Logik
		// request.getRequestDispatcher("DispatcherServlet").forward(request,
		// response);
		//
		// }
		// Letzter Schritt Benutzer registrieren
		if (id.equals("CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER")) {

			String fehlernachricht = "";
			try {
				String titel = request.getParameter("Titel");
				if (titel != null && titel.equals("kein Titel")) {
					titel = null;
				}
				String vorname = request.getParameter("Vorname");
				String nachname = request.getParameter("Nachname");
				char geschlecht = NullKonstanten.NULL_CHAR;
				String passwort = null;
				String email = request.getParameter("Email");
				String telefon = request.getParameter("Telefon");
				String fax = request.getParameter("fax");
				// TODO dirty fix
				String handynummer = "123123123123";
				String institut = request.getParameter("Institut");
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
				PersonBean aPerson = new PersonBean(nachname, vorname, titel,
						geschlecht, email, telefon, handynummer, fax);
				BenutzerkontoBean aBenutzerkonto = new BenutzerkontoBean(email,
						passwort, aPerson);
				Benutzerkonto.anlegenBenutzer(aBenutzerkonto);

			} catch (IllegalArgumentException e) {
				fehlernachricht += e.getMessage();
			}
			// Daten waren nicht fehlerfrei
			if (fehlernachricht != "") {
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