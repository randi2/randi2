package de.randi2.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankFehlerException;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.ZentrumBean;

/**
 * Diese Klasse repraesentiert das ZENTRUMSERVLET, welches Aktionen an die
 * Zentrum-Fachklasse und an den DISPATCHER weiterleitet.
 * 
 * @version $Id: ZentrumServlet.java 2418 2007-05-04 14:37:12Z jthoenes $
 * @author Andreas Freudling [afreudling@hs-heilbronn.de]
 * 
 */
public class ZentrumServlet extends javax.servlet.http.HttpServlet {

	/**
	 * Default Serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die Anfrage_id zur Verwendung im Dispatcher Servlet
	 */
	public enum anfrage_id {

		/**
		 * Schritt, waehrend dem die Liste der Zentren geholt wird.
		 */
		CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI,

		/**
		 * Pruefung des Zentrumpassworts bei Benutzerregistierung
		 */
		CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI

	}

	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ZentrumServlet() {
		super();
	}

	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen. Hier werden Anfragen verarbeitet, die Zentren betreffen.
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

		// Benutzer registrieren
		// Schritt 2.1
		if (id.equals(ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI.name())) {
			this.classDispatcherservletBenutzerRegistrierenZwei(request, response);

		}
		// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
		// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
		else if (id.equals(ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI.name())) {
			this.classDispatcherservletBenutzerRegistrierenDrei(request, response);
		} else {
			// TODO Hier muss noch entschieden werden,was passiert
		}
	}

	/**
	 * Methode ermittelt die Liste alle vorhandenen Zentren, setzt sie als das Attribut listeZentren, und zeigt diese dem Benutzer beim registrieren an.
	 * @param request Requestobjekt
	 * @param response Responseobjekt
	 * @throws ServletException Fehler in der Http-Verarbeitung
	 * @throws IOException Fehler in der IO-Verarbaitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest
	 *      request, HttpServletResponse response)
	 */
	private void classDispatcherservletBenutzerRegistrierenZwei(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Nach allen vorhandenen Zentren suchen
		// @Andy - wieder die gleiche Geschichte, mit der Konstante
		// (siehe
		// mein Kommentar in dem BenutzerServlet Zeile: 171)
		ZentrumBean sZentrum = new ZentrumBean();
		sZentrum.setIstAktiviert(true);
		sZentrum.setFilter(true);
		Vector<ZentrumBean> gZentrum = null;

		try {
			gZentrum = Zentrum.suchenZentrum(sZentrum);
		} catch (DatenbankFehlerException e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e.getMessage());
		}
		request.setAttribute("listeZentren", gZentrum);
		// Schritt 2.1.3
		request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
	}

	/**
	 * Methode wird aufgerufen um bei der Benutzerregistrierung nach Zentren zu filtern, bzw. das Zentrumpasswort zu ueberpruefen
	 * @param request Requestobjekt
	 * @param response Responseobjekt
	 * @throws ServletException Fehler in der Http-Verarbeitung
	 * @throws IOException Fehler in der IO-Verarbaitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest
	 *      request, HttpServletResponse response)
	 */
	private void classDispatcherservletBenutzerRegistrierenDrei(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Filterung
		if (((String) request.getParameter("Filtern")) != null) {
			try {
				Vector<ZentrumBean> gZentrum = null;
				if (((String) request.getParameter("name_institution")) != "" && ((String) request.getParameter("name_abteilung")) != "") {
					ZentrumBean sZentrum = new ZentrumBean();

					// Filter setzen
					sZentrum.setFilter(true);
					sZentrum.setInstitution(request.getParameter("name_institution"));
					sZentrum.setAbteilung(request.getParameter("name_abteilung"));
					sZentrum.setIstAktiviert(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);

				} else {
					ZentrumBean sZentrum = new ZentrumBean();
					sZentrum.setFilter(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);
				}
				request.setAttribute("listeZentren", gZentrum);
			} catch (BenutzerException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e.getMessage());
			} catch (DatenbankFehlerException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e.getMessage());
			}
			
			request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
		} else {
			try {
				// Erstmal alle vorhandenen Zentren suchen
				ZentrumBean sZentrum = new ZentrumBean();
				sZentrum.setFilter(true);
				sZentrum.setIstAktiviert(true);
				Vector<ZentrumBean> gZentrum = null;

				gZentrum = Zentrum.suchenZentrum(sZentrum);

				Iterator<ZentrumBean> itgZentrum = gZentrum.iterator();
				while (itgZentrum.hasNext()) {
					ZentrumBean aZentrumBean = itgZentrum.next();
					String suche = "bestaetigen" + aZentrumBean.getId();
					if (request.getParameter(suche) != null) {
						Zentrum aZentrum = new Zentrum(aZentrumBean);
						// Zentrum Passwort richtig
						if (aZentrum.pruefenPasswort(request.getParameter("zentrum_passwort" + aZentrumBean.getId()))) {
							//Zentrum an die Session binden
							request.getSession().setAttribute(DispatcherServlet.sessionParameter.ZENTRUM_BENUTZER_ANLEGEN.toString(),aZentrum.getZentrumBean());
							request.setAttribute("aZentrum", aZentrum.getZentrumBean().getId());
							request.getRequestDispatcher("/benutzer_anlegen_drei.jsp").forward(request, response);
						}
						// Zentrum Passwort falsch
						else {
							request.setAttribute("listeZentren", gZentrum);
							request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, "Falsches Zentrumpasswort");
							request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
						}
					}
				}
			} catch (DatenbankFehlerException e) {
				//Fehler zurück!
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e.getMessage());
				request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(request, response);
			}
		}

		// keine Filterung
	}
}
