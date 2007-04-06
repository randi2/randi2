package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;

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
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
	}

	// TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
	/**
	 * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
	 * entgegen.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = (String) request.getParameter("anfrage_id");

		// logout (wirklich an dieser Stelle?? oder in BenutezrServelet)
		if (id.equals("JSP_HEADER_LOGOUT")) {

			request.getSession().invalidate();
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);

		}

	}

	// TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = (String) request.getParameter("anfrage_id");
		String idAttribute = (String) request.getAttribute("anfrage_id");
		if (idAttribute != null) {
			id = idAttribute;
		}
		Logger.getLogger(this.getClass()).debug(id);

		// Login
		if (id.equals("JSP_INDEX_LOGIN")) {
			request
					.setAttribute("anfrage_id",
							"CLASS_DISPATCHERSERVLET_LOGIN1");
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);
		} else if (id.equals("CLASS_BENUTZERSERVLET_LOGIN_ERROR")) {
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else if (id.equals("CLASS_BENUTZERSERVLET_LOGIN_OK")) {

			BenutzerkontoBean aBenutzer = (BenutzerkontoBean) request
					.getSession().getAttribute("aBenutzer");
			Logger.getLogger(this.getClass()).debug(aBenutzer);
			if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.STUDIENARZT) {
				request.getRequestDispatcher("/studie_auswaehlen.jsp").forward(
						request, response);
			} else if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.STUDIENLEITER) {
				request.getRequestDispatcher("/studie_auswaehlen.jsp").forward(
						request, response);
			} else if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.STATISTIKER) {
				request.getRequestDispatcher("/studie_ansehen.jsp").forward(
						request, response);
			} else if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.ADMIN) {
				request.getRequestDispatcher("/global_welcome.jsp").forward(
						request, response);
			} else if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.SYSOP) {
				request.getRequestDispatcher("/global_welcome.jsp").forward(
						request, response);
			}// else if
		}// else if

		// Benutzer registrieren
		// Schritt 1.1: STARTSEITE->DISCLAIMER
		else if (id.equals("JSP_INDEX_BENUTZER_REGISTRIEREN_EINS")) {

			request.getRequestDispatcher("/benutzer_anlegen_eins.jsp").forward(
					request, response);

		}
		// Schritt 2.1:DISCLAIMER->ZENTRUMAUSWAHL
		else if (id
				.equals("JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI")) {

			request.setAttribute("anfrage_id",
					"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
			request.getRequestDispatcher("ZentrumServlet").forward(request,
					response);
		}

		// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
		// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
		else if (id
				.equals("JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI")) {
			request.setAttribute("anfrage_id",
					"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI");
			request.getRequestDispatcher("ZentrumServlet").forward(request,
					response);

		}

		// Schritt 4: BENUTZERDATEN_EINGEBEN->
		else if (id
				.equals("JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER")) {
			request.setAttribute("anfrage_id",
					"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER");
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);

		}
		// else if
		// (id.equals("CLASS_BENUTZERSERVLET_BENUTZER_REGISTRIEREN_VIER"))
		// {
		//		
		// request.getRequestDispatcher("/benutzer_anlegen_vier.jsp").forward(request,
		// response);
		// }

	}// doPost
}// DispatcherServlet
