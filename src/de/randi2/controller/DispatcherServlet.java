package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.Config;
import de.randi2.utility.Jsp;
import de.randi2.utility.JspTitel;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
import de.randi2.utility.Parameter;
import de.randi2.utility.Config.Felder;

/**
 * <p>
 * Diese Klasse repraesentiert den DISPATCHER (== Weiterleiter). Dieser wird von
 * jeder Anfrage im Projekt angesprochen und leitet diese dann an die
 * entsprechenden Unterservlets bzw. direkt an JSPs weiter.
 * </p>
 * 
 * @version $Id: DispatcherServlet.java 2462 2007-05-08 12:20:08Z btheel $
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * 
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
	// TODO alle Parameter auf Enums umstellen. Task ist Code-Kosmetik, daher
	// zweitrangig --BTheel
	/**
	 * Ist System gesperrt oder nicht.
	 */
	private boolean istSystemGesperrt = true;

	/**
	 * Fehlermeldung, wenn das System gesperrt ist.
	 */
	private String meldungSystemGesperrt = "Meldung des System ist gesperrt";

	/**
	 * Name der Fehlervariablen im Request. Ueber diesen Parameternamen sind die
	 * Fehlermeldungen von der GUI aus dem Request zu gewinnen
	 */
	public static final String FEHLERNACHRICHT = "fehlernachricht";

	/**
	 * Name der Nachricht_ok -Variablen im Reqeust. Wird in
	 * inc_nachricht_erfolgreich.jsp. Sonst nicht verwenden!!!
	 */
	public static final String NACHRICHT_OK = "nachricht_ok";

	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
		istSystemGesperrt = Boolean.valueOf(Config
				.getProperty(Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG));
		Logger.getLogger(this.getClass()).debug(
				"Lade Mitteilung (System gesperrt) aus Config");
		meldungSystemGesperrt = Config
				.getProperty(Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
	}

	/**
	 * Verwaltet alle anfrage_ids an das DispatcherServlet. Alle anfrage_id's
	 * muesen hier deklariert werden.
	 * 
	 */
	public enum anfrage_id {
		/**
		 * Benutzer loggt sich aus.
		 */
		JSP_HEADER_LOGOUT,

		/**
		 * Benutzer loggt sich ein.
		 */
		JSP_INDEX_LOGIN,

		/**
		 * Benutzer klickt Impressum link
		 */
		JSP_HEADER_IMPRESSUM,

		/**
		 * Benutzer klickt Benutzer registieren auf index.jsp
		 */
		JSP_INDEX_BENUTZER_REGISTRIEREN_EINS,

		/**
		 * Admin möchte ein neues Zentrum anlegen
		 */
		JSP_ZENTRUM_ANLEGEN,

		/**
		 * Ein Nutzer lässt sich ein neues Passwort zuschicken
		 */
		JSP_PASSWORT_VERGESSEN,

		/**
		 * Benutzer hat Disclaimer akzeptiert. (Benutzer registrieren)
		 */
		JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI,

		/**
		 * Der Admin möchte sich einen Benutzer anzeigen lassen, oder sperren.
		 */
		JSP_BENUTZER_LISTE_ADMIN_ANZEIGEN_SPERREN,

		/**
		 * Benutzer filtert nach Zentren bzw. gibt sein Zentrumspasswort ein.
		 * (Benutzer registieren)
		 */
		JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI,

		/**
		 * 
		 * Benutzer gibt Personendaten ein (Benutzer registrieren)
		 */
		JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER,

		/**
		 * Zentrenverwaltung für Studie
		 */
		JSP_ZENTRUM_ANZEIGEN,

		/**
		 * Benutzerdaten aendern
		 */
		JSP_DATEN_AENDERN,

		/**
		 * Im Menue wird Daten aendern angklickt
		 */
		JSP_INC_MENUE_DATEN_AENDERN,

		/**
		 * Der Admin lässt sich die Studien anzeigen
		 */
		JSP_INC_MENUE_STUDIEN_ANZEIGEN,

		/**
		 * Im Menue wird Patient hinzufuegen gewählt
		 */
		JSP_INC_MENUE_PATIENT_HINZUFUEGEN,
		/**
		 * Im Menue wird die Studienaerzte_Liste angeforderz
		 */
		JSP_INC_MENUE_STUDIENAERZTE_LISTE,

		/**
		 * Im Menue wird die Liste der Admins angefordert
		 */
		JSP_INC_MENUE_ADMIN_LISTE,

		/**
		 * Im Menue wird Systemadministration gewählt
		 */
		JSP_INC_MENUE_SYSTEMADMINISTRATION,

		/**
		 * System sperren wird aufgerufen
		 */
		JSP_INC_MENUE_SYSTEMSPERREN,

		/**
		 * Admin anlegen wird geklickt
		 */
		JSP_INC_MENUE_ADMIN_ANLEGEN,

		/**
		 * Ein Studienleiter soll angelegt werden.
		 */
		JSP_INC_MENUE_STUDIENLEITER_ANLEGEN,
		
		/**
		 * Admin möchte Zentrum anlegen
		 */
		JSP_INC_MENUE_ZENRUM_ANLEGEN,

		/**
		 * Leitet den Forward der system_sperren.jsp weiter (kommt als get)
		 */
		JSP_SYSTEM_SPERREN,

		/**
		 * Aufforderung das System zu Entsperren
		 */
		AKTION_SYSTEM_ENTSPERREN,
		/**
		 * Aufforderung das System zu Sperren
		 */
		AKTION_SYSTEM_SPERREN,

		/**
		 * Aufforderung, den Benutzer aus dem System abzumelden
		 */
		AKTION_LOGOUT,

		/**
		 * Aufforderung, den Nachrichtendienst anzuzeigen
		 */
		JSP_HEADER_NACHRICHTENDIENST,

		/**
		 * Aufforderung, die Hilfe anzuzeigen
		 */
		JSP_HEADER_HILFE,

		/**
		 * Aufforderung, einen Admin mit den gesendeten Daten anzulegen
		 */
		AKTION_ADMIN_ANLEGEN,
		
		/**
		 * Aktion ein Zentrum einer Studie zuzuweisen
		 */
		AKTION_ZENTRUM_ZUWEISEN,

		/**
		 * Aufforderung, den Request an die entsprechende Seite umzuleiten
		 */
		JSP_ADMIN_ANLEGEN,

		/**
		 * Aufforderung, einen Studienleiter mit den gesendeten Daten anzulegen
		 */
		AKTION_STUDIENLEITER_ANLEGEN,

		/**
		 * Benutzer suchen.
		 */
		BENUTZER_SUCHEN,

		/**
		 * Zentrum aendern
		 */
		JSP_ZENTRUM_AENDERN,

		/**
		 * "Neue Studie anlegen" auswaehlen
		 */
		JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE,

		/**
		 * Neue Studie anlegen
		 */
		JSP_STUDIE_ANLEGEN,

		/**
		 * Refresh des Algorithmusses
		 */
		JSP_STUDIE_ANLEGEN_REFRESH,

		/**
		 * Neuen Studienarm zu Studie hinzufuegen
		 */
		JSP_STUDIE_ANLEGEN_ADD_STUDIENARM,

		/**
		 * Studienarm von Studie entfernen
		 */
		JSP_STUDIE_ANLEGEN_DEL_STUDIENARM,
		/**
		 * Neuen Strata zu Studie hinzufuegen
		 */
		JSP_STUDIE_ANLEGEN_ADD_STRATA,

		/**
		 * Strata von Studie entfernen
		 */
		JSP_STUDIE_ANLEGEN_DEL_STRATA,
		/**
		 * Studie aendern
		 */
		JSP_STUDIE_AENDERN,
		/**
		 * Refresh des Algorithmusses
		 */
		JSP_STUDIE_AENDERN_REFRESH,

		/**
		 * Neuen Studienarm zu Studie hinzufuegen
		 */
		JSP_STUDIE_AENDERN_ADD_STUDIENARM,

		/**
		 * Studienarm von Studie entfernen
		 */
		JSP_STUDIE_AENDERN_DEL_STUDIENARM,
		/**
		 * Neuen Strata zu Studie hinzufuegen
		 */
		JSP_STUDIE_AENDERN_ADD_STRATA,

		/**
		 * Strata von Studie entfernen
		 */
		JSP_STUDIE_AENDERN_DEL_STRATA,
		/**
		 * Studie auswaehlen
		 */
		JSP_STUDIE_AUSWAEHLEN,

		/**
		 * Simulation einer Studie.
		 */
		JSP_SIMULATION,
		/**
		 * Zentrum anzeigen beim Admin.
		 */
		ZENTRUM_ANZEIGEN_ADMIN,
		/**
		 * Zentrum ansehen
		 */
		JSP_ZENTRUM_ANSEHEN,
		/**
		 * Studie ansehen
		 */
		JSP_STUDIE_ANSEHEN,
		/**
		 * Studie ansehen
		 */
		JSP_STUDIE_ANSEHEN_AENDERN,
		/**
		 * Button bei Zentrum suchen.
		 */
		ZENTRUM_AENDERN_SPERREN,

		/**
		 * Button bei Patient hinzufuegen.
		 */
		JSP_PATIENT_HINZUFUEGEN_AUSFUEHREN,

		/**
		 * Ein Benutzer soll gesperrt bzw. entsperrt werden.
		 */
		JSP_BENUTZER_SPERREN_SPERREN_ENTSPERREN,

		/**
		 * Ergebnisse
		 */
		JSP_ERGEBNISSE,

		/**
		 * Export als CSV
		 */
		JSP_ERGEBNISSE_EXPORT_CSV,
		
		/**
		 * Export als XLS
		 */
		JSP_ERGEBNISSE_EXPORT_XLS, 
		
		/**
		 * Aufforderung, eine Nachricht zu versenden
		 */
		AKTION_NACHRICHT_VERSENDEN,
		
		/**
		 * Aktion zum Entziehen eines Zentrums von einer Studie
		 */
		AKTION_ZENTRUM_ENTZIEHEN;;
	}

	/**
	 * Enhaelt die Parameternamen, die in der Session gesetzt werden koennen
	 * 
	 */
	public static enum sessionParameter {
		/**
		 * Konto des Benutzers (BenutzerkontoBean)
		 */
		A_Benutzer("aBenutzer"), // XXX Konto ist als 'aBenutzer' gebunden,
		// nicht ueber diese
		// Kosntante (lplotni 17. Juni: warum denn ?)

		/**
		 * Zentrum fuer das sich der Benutzer anmeldet.
		 */
		ZENTRUM_BENUTZER_ANLEGEN("aZentrum"),

		/**
		 * Die von dem Benutzer ausgewählte, aktuelle Studie
		 */
		AKTUELLE_STUDIE("aStudie"),
		
		/**
		 * Wird an die Session gebunden, wenn der Admin einen Benutzer sperren,
		 * entsperren will.
		 */
		BENUTZER_SPERREN_ENTSPERREN_ADMIN("bSperren");

		/**
		 * String Version des Parameters
		 */
		private String parameter = null;

		/**
		 * Setzt die String Repraesentation des Parameters
		 * 
		 * @param parameter
		 *            String Repraesentation des Parameters
		 */
		private sessionParameter(String parameter) {
			this.parameter = parameter;
		}

		/**
		 * Liefert die String Repraesentation des Parameters
		 * 
		 * @return String Repraesentation des Parameters
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.parameter;
		}
	}

	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 * 
	 */
	public static enum requestParameter {

		/**
		 * Titel der aktuellen JSP
		 */
		TITEL("titel"),

		/**
		 * Systemstatus gesperrt[true|false] (boolean)
		 */
		IST_SYSTEM_GESPERRT("system_gesperrt"),

		/**
		 * Haelt die Begruendung der Systemsperrung (String)
		 */
		MITTEILUNG_SYSTEM_GESPERRT("mitteilung_system_gesperrt"),

		/**
		 * Anzahl an Strata
		 */
		ANZAHL_STRATA("anzahl_strata"),
		/**
		 * Anzahl an Armen
		 */
		ANZAHL_ARME("anzahl_arme"),

		/**
		 * Liste der Zentren
		 */
		LISTE_ZENTREN("listeZentren");

		/**
		 * String Version des Parameters
		 */
		private String parameter = null;

		/**
		 * Setzt die String Repraesentation des Parameters
		 * 
		 * @param parameter
		 *            String Repraesentation des Parameters
		 */
		private requestParameter(String parameter) {
			this.parameter = parameter;
		}

		/**
		 * Liefert die String Repraesentation des Parameters
		 * 
		 * @return String Repraesentation des Parameters
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.parameter;
		}

	}

	// TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
	/**
	 * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
	 * entgegen.
	 * <p>
	 * Ist das System geperrt, so wird die Anfrage auf die Seite
	 * <source>index_gesperrt.jsp</source> umgeleitet, ansonsten wird die Seite
	 * <source>index.jsp</source> aufgerufen.
	 * </p>
	 * <p>
	 * Es ist erforderlich, das fuer einen gueltigen Aufruf sowohl eine
	 * anfrage_id gesetzt als auch der Benutzer eingeloggt ist.
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
		doPost(request, response);
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

		String id = (String) request.getParameter(Parameter.anfrage_id);
		// idAttribute nicht entfernen, benutzen dies fuer die Weiterleitung aus
		// dem Benutzerservlet --Btheel
		String idAttribute = (String) request
				.getAttribute(Parameter.anfrage_id);
		// bei jedem Zugriff, Titel zuruecksetzen
		request.setAttribute(DispatcherServlet.requestParameter.TITEL
				.toString(), null);
		// falls ID null dann leite auf den Index weiter
		if ((id == null || id.trim().equals("")) && (idAttribute == null)) {

			weiterleitungAufIndex(request, response);
		} else {
			Logger.getLogger(this.getClass()).debug("[POST]anfrage_id: " + id);
			if (idAttribute != null) {
				id = idAttribute;
			}

			// WEITERLEITUNGEN FUER BENUTZERSERVLET
			// [start]
			// Login
			if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name())) {
				weiterleitungBenutzerAnmelden(request, response);
			}

			// Benutzer registrieren
			// Schritt 1.1: STARTSEITE->DISCLAIMER
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS
							.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL
						.toString(), "Benutzer anlegen");
				request.getRequestDispatcher("/benutzer_anlegen_eins.jsp")
						.forward(request, response);
			}
			// Schritt 2.1:DISCLAIMER->ZENTRUMAUSWAHL
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}

			// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
			// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI");
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}

			// Schritt 4: BENUTZERDATEN_EINGEBEN->
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER");
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);

			} else if (id
					.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN
							.name())) {
				if (!isBenutzerAngemeldet(request)) { // Benutzer nicht
					// angemeldet
					BenutzerkontoBean anonymous = new BenutzerkontoBean();
					// FIXME FRAGE LogAktion mit String anstatt
					// BenutzerkontoBean
					// zum Loggen der IP?
					// anonymous.setBenutzername("Unangemeldeter Benutzer [IP:
					// "+request.getRemoteAddr()+"]");

					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne Login", anonymous);
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				if (!(((BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer")).getRolle())
						.besitzenRolleRecht(Recht.Rechtenamen.SYSTEM_SPERREN)) {
					// Der User besitzt keine entsprechenden Rechte
					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne ausreichende Rechte"
									+ getMeldungSystemGesperrt(),
							(BenutzerkontoBean) request.getSession()
									.getAttribute("aBenutzer"));
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				this.setSystemGesperrt(false);
				Logger.getLogger(this.getClass()).debug(
						"Schalte System wieder frei");
				LogAktion a = new LogAktion("System wurde entsperrt",
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
				Logger.getLogger(LogLayout.ADMINISTRATION).info(a);
				request.getRequestDispatcher("/system_sperren.jsp").forward(
						request, response);
				return;

			} else if (id
					.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN
							.name())) {
				if (!isBenutzerAngemeldet(request)) { // Benutzer nicht
					// angemeldet
					BenutzerkontoBean anonymous = new BenutzerkontoBean();
					// FIXME FRAGE LogAktion mit String anstatt
					// BenutzerkontoBean
					// zum Loggen der IP?
					// anonymous.setBenutzername("Unangemeldeter Benutzer [IP:
					// "+request.getRemoteAddr()+"]");

					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne Login", anonymous);
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				if (!(((BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer")).getRolle())
						.besitzenRolleRecht(Recht.Rechtenamen.SYSTEM_SPERREN)) {
					// Der User besitzt keine entsprechenden Rechte
					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne ausreichende Rechte"
									+ getMeldungSystemGesperrt(),
							(BenutzerkontoBean) request.getSession()
									.getAttribute("aBenutzer"));
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				this.setSystemGesperrt(true);

				String meldung = StringEscapeUtils
						.escapeHtml((String) request
								.getParameter(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
										.toString()));
				this.setMeldungSystemGesperrt(meldung);

				Logger.getLogger(this.getClass()).debug(
						"Sperre System. Grund: '" + getMeldungSystemGesperrt()
								+ "'");
				LogAktion a = new LogAktion("System wurde gesperrt, Grund: '"
						+ getMeldungSystemGesperrt() + "'",
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
				Logger.getLogger(LogLayout.ADMINISTRATION).info(a);
				request.getRequestDispatcher("/system_sperren.jsp").forward(
						request, response);
				return;
			} else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
							.name())) {
				weiterleitungSystemSperrung(request, response);
				return;
			} else if (id.equals(anfrage_id.AKTION_ADMIN_ANLEGEN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an BenutzerServlet weiter");
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_ANLEGEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_DATEN_AENDERN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an BenutzerServlet weiter");
				request
						.setAttribute(
								"anfrage_id",
								BenutzerServlet.anfrage_id.BENUTZERDATEN_AENDERN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_ZENTRUM_AENDERN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an ZentrumServlet weiter");
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.ZENTRUM_AENDERN.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_INC_MENUE_ADMIN_ANLEGEN.name()) || id.equals(anfrage_id.JSP_ADMIN_ANLEGEN.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL
						.toString(), JspTitel.ADMIN_ANLEGEN.toString());
				
				ZentrumServlet.bindeZentrenListeAnRequest(request);
				request.getRequestDispatcher(Jsp.ADMIN_ANLEGEN).forward(
						request, response);
			}

			// [end]
			// WEITERLEITUNGEN FUER ZENTRUMSERVLET
			// [start]
			
			
			
			else if (id.equals(anfrage_id.AKTION_ZENTRUM_ZUWEISEN.name())) {
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ZUWEISEN.name());
				if ((request.getParameter(Parameter.zentrum.ZENTRUM_ID
						.toString()) != null)) {
					// ID vorhanden, zentrumdetails werden angezeigt
					String idx = request
							.getParameter(Parameter.zentrum.ZENTRUM_ID
									.toString());
					request.setAttribute(Parameter.zentrum.ZENTRUM_ID
							.toString(), idx);

					request.getRequestDispatcher("ZentrumServlet").forward(
							request, response);
					
				} else {
					// aus irgendwelchen Gründen Zentrum_ID nicht vorhanden,
					// leite zurück letzten seite
					if (request.getParameter(Parameter.filter) == null) {
						request.setAttribute("listeZentren", null);
					}
					request.setAttribute(Parameter.anfrage_id.toString(),
							StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN
									.name());
					request.getRequestDispatcher("StudieServlet").forward(
							request, response);
				}

			}
			
			
			else if (id.equals(anfrage_id.AKTION_ZENTRUM_ENTZIEHEN.name())) {
					request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ENTZIEHEN.name());
				if ((request.getParameter(Parameter.zentrum.ZENTRUM_ID
						.toString()) != null)) {
					// ID vorhanden, zentrumdetails werden angezeigt
					String idx = request
							.getParameter(Parameter.zentrum.ZENTRUM_ID
									.toString());
					request.setAttribute(Parameter.zentrum.ZENTRUM_ID
							.toString(), idx);

					request.getRequestDispatcher("ZentrumServlet").forward(
							request, response);
					
				} else {
					// aus irgendwelchen Gründen Zentrum_ID nicht vorhanden,
					// leite zurück letzten seite
					if (request.getParameter(Parameter.filter) == null) {
						request.setAttribute("listeZentren", null);
					}
					request.setAttribute(Parameter.anfrage_id.toString(),
							StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN
									.name());
					request.getRequestDispatcher("StudieServlet").forward(
							request, response);
				}

			}
			

			else if (id.equals(anfrage_id.JSP_ZENTRUM_ANSEHEN.name())) {
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name());
				if ((request.getParameter(Parameter.zentrum.ZENTRUM_ID
						.toString()) != null)) {
					// ID vorhanden, zentrumdetails werden angezeigt
					String idx = request
							.getParameter(Parameter.zentrum.ZENTRUM_ID
									.toString());
					request.setAttribute(Parameter.zentrum.ZENTRUM_ID
							.toString(), idx);

					request.getRequestDispatcher("ZentrumServlet").forward(
							request, response);
				} else {
					// aus irgendwelchen Gründen Zentrum_ID nicht vorhanden,
					// leite zurück letzten seite
					if (request.getParameter(Parameter.filter) == null) {
						request.setAttribute("listeZentren", null);
					}
					request.setAttribute(Parameter.anfrage_id.toString(),
							StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN
									.name());
					request.getRequestDispatcher("StudieServlet").forward(
							request, response);
				}

			}

			else if (id.equals(anfrage_id.JSP_ZENTRUM_ANLEGEN.name())) {
				request
						.setAttribute(
								"anfrage_id",
								ZentrumServlet.anfrage_id.ClASS_DISPATCHERSERVLET_ZENTRUM_ANLEGEN
										.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}
			// Benutzer suchen
			else if (id.equals(anfrage_id.BENUTZER_SUCHEN.name())) {
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			}

			else if (id.equals(anfrage_id.JSP_PASSWORT_VERGESSEN.name())) {
				request
						.setAttribute(
								Parameter.anfrage_id,
								BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_PASSWORT_VERGESSEN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			}

			// [end]

			// WEITERLEITUNG FUER STUDIESERVLET
			// [start]

			
			
			else if (id.equals(anfrage_id.JSP_STUDIE_ANSEHEN.name())) {
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.JSP_STUDIE_ANSEHEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STUDIENARM_ANZEIGEN
							.toString())) {
				// Der Benutzer will sich ein Studienarm anzeigen lassen
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STUDIENARM_ANZEIGEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STRATA_ANZEIGEN
							.toString())) {
				// Der Benutzer will sich die Strata anzeigen lassen
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STRATA_ANZEIGEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STATISTIK_ANZEIGEN
							.toString())) {
				// Der Benutzer will sich eine Statistik anzeigen lassen
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STATISTIK_ANZEIGEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			}else if (id.equals(anfrage_id.JSP_ZENTRUM_ANZEIGEN.name())) {

				if (request.getParameter(Parameter.filter) == null) {
					request.setAttribute("listeZentren", null);
				}
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE
					.name())) {

				// neue Studie anlegen
				request
						.setAttribute(
								Parameter.anfrage_id.toString(),
								StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN_NEUESTUDIE
										.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN.toString())) {
				if (request.getParameter(Parameter.filter) == null) {
					// Studie wurde ausgewaehlt
					request.setAttribute(Parameter.anfrage_id.toString(),
							StudieServlet.anfrage_id.AKTION_STUDIE_AUSGEWAEHLT
									.toString());

				} else {
					// auf der studie_auswaehlen.jsp wird die liste gefiltert
					request.setAttribute(Parameter.anfrage_id.toString(),
							StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN
									.toString());

				}
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STUDIE_ANSEHEN_PROTOKOLL_DOWNLOAD
							.toString())) {
				// Das Studienprotokoll wird heruntergeladen
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an DownloadServlet weiter");
				request.setAttribute(
						DownloadServlet.requestParameter.DATEI_NAME.toString(),
						((StudieBean) request.getSession().getAttribute(
								sessionParameter.AKTUELLE_STUDIE.toString()))
								.getStudienprotokollpfad());
				request.setAttribute(DownloadServlet.requestParameter.DATEI_ART
						.toString(), DownloadServlet.dateiArt.STUDIENPROTOKOLL);
				request.getRequestDispatcher("DownloadServlet").forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_SIMULATION.toString())) {
				// Weiterleitung auf die Simulation seite
				request.getRequestDispatcher(Jsp.SIMULATION).forward(request,
						response);
			} else if (id.equals(StudieServlet.anfrage_id.JSP_STATISTIKER_ANLEGEN.toString())) {
				// Ein Statistiker Account soll angelegt werden - Weiterleitung zur StudieServlet
				request.setAttribute(Parameter.anfrage_id, StudieServlet.anfrage_id.AKTION_STATISTIKER_ANLEGEN.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id.equals(StudieServlet.anfrage_id.JSP_STAT_PASSWORT_ERZEUGEN.toString())) {
				// Ein neues Passwort soll für den vorhandenen Statistiker erzeugt werden - Weiterleitung zur StudieServlet
				request.setAttribute(Parameter.anfrage_id, StudieServlet.anfrage_id.AKTION_STAT_PASSWORT_ERZEUGEN.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STUDIENARM_BEENDEN
							.toString())) {
				// Ein Arm soll beendet werden
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STUDIENARM_BEENDEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN.name())) {


				// neue Studie anlegen
				request.setAttribute(Parameter.anfrage_id.toString(),
						StudieServlet.anfrage_id.AKTION_STUDIE_ANLEGEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_REFRESH.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));
				request.setAttribute(Parameter.studie.STATISTIKER_BOOL.name(),
						request.getParameter(Parameter.studie.STATISTIKER_BOOL
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STRATA
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));
				request.setAttribute(Parameter.studie.STATISTIKER_BOOL.name(),
						request.getParameter(Parameter.studie.STATISTIKER_BOOL
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								Integer
										.toString(Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
																.toString())) + 1));
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STUDIENARM
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));
				request.setAttribute(Parameter.studie.STATISTIKER_BOOL.name(),
						request.getParameter(Parameter.studie.STATISTIKER_BOOL
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
																.toString()))) + 1));
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STRATA
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));
				request.setAttribute(Parameter.studie.STATISTIKER_BOOL.name(),
						request.getParameter(Parameter.studie.STATISTIKER_BOOL
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
																.toString()))) - 1));
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STUDIENARM
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));
				request.setAttribute(Parameter.studie.STATISTIKER_BOOL.name(),
						request.getParameter(Parameter.studie.STATISTIKER_BOOL
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
																.toString()))) - 1));
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STUDIE_PAUSIEREN_JA
							.toString())) {
				// Studie pausieren - wird durchgefürhrt
				request.setAttribute(Parameter.anfrage_id.toString(),
						StudieServlet.anfrage_id.AKTION_STUDIE_PAUSIEREN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_STUDIE_FORTSETZEN_JA
							.toString())) {
				// Studie fortsetzen - wird durchgeführt
				request.setAttribute(Parameter.anfrage_id.toString(),
						StudieServlet.anfrage_id.AKTION_STUDIE_FORTSETZEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(StudieServlet.anfrage_id.JSP_STUDIE_FORTSETZEN
					.toString())) {
				// studie_fortsetzen.jsp wird dem Benutzer angezeigt!
				request.getRequestDispatcher(Jsp.STUDIE_FORTSETZEN).forward(
						request, response);
			} else if (id.equals(StudieServlet.anfrage_id.JSP_STUDIE_PAUSIEREN
					.toString())) {
				// studie_pausieren.jsp wird dem Benutzer angezeigt!
				request.getRequestDispatcher(Jsp.STUDIE_PAUSIEREN).forward(
						request, response);
			} else if (id.equals(StudieServlet.anfrage_id.JSP_STUDIE_STARTEN
					.toString())) {
				// studie_starten.jsp wird dem Benutzer angezeigt!
				request.getRequestDispatcher(Jsp.STUDIE_STARTEN).forward(
						request, response);
			} else if (id.equals(StudieServlet.anfrage_id.JSP_STUDIE_STARTEN_JA
					.toString())) {
				// Die Studie soll gestartet werden
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STUDIE_STARTEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_STUDIE_ANSEHEN_AENDERN
					.toString())) {
				// Die Studie soll gestartet werden
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN.name())) {


				// neue Studie anlegen
				request.setAttribute(Parameter.anfrage_id.toString(),
						StudieServlet.anfrage_id.AKTION_STUDIE_AENDERN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN_REFRESH.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN_ADD_STRATA
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));


				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								Integer
										.toString(Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
																.toString())) + 1));
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN_ADD_STUDIENARM
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));


				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
																.toString()))) + 1));
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN_DEL_STRATA
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));


				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
																.toString()))) - 1));
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AENDERN_DEL_STUDIENARM
					.name())) {

				request.setAttribute(Parameter.studie.NAME.name(), request
						.getParameter(Parameter.studie.NAME.name()));
				request.setAttribute(Parameter.studie.BESCHREIBUNG.name(),
						request.getParameter(Parameter.studie.BESCHREIBUNG
								.name()));
				request.setAttribute(Parameter.studie.STARTDATUM.name(),
						request
								.getParameter(Parameter.studie.STARTDATUM
										.name()));
				request.setAttribute(Parameter.studie.ENDDATUM.name(), request
						.getParameter(Parameter.studie.ENDDATUM.name()));
				request.setAttribute(Parameter.studie.STUDIENPROTOKOLL.name(),
						request.getParameter(Parameter.studie.STUDIENPROTOKOLL
								.name()));
				request
						.setAttribute(
								Parameter.studie.RANDOMISATIONSALGORITHMUS
										.name(),
								request
										.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
												.name()));
				request.setAttribute(Parameter.studie.BLOCKGROESSE.name(),
						request.getParameter(Parameter.studie.BLOCKGROESSE
								.name()));


				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.strata.STRATABESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.strata.STRATABESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.strata.NAME.name() + i,
							request.getParameter(Parameter.strata.NAME.name()
									+ i));
					request.setAttribute(Parameter.strata.AUSPRAEGUNGEN.name()
							+ i, request
							.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()
									+ i));
				}

				for (int i = 1; i < Integer
						.parseInt((String) request
								.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString())) + 1; i++) {

					request.setAttribute(Parameter.studienarm.ARMBESCHREIBUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.ARMBESCHREIBUNG
									.name()
									+ i));
					request.setAttribute(Parameter.studienarm.BEZEICHNUNG
							.name()
							+ i, request
							.getParameter(Parameter.studienarm.BEZEICHNUNG
									.name()
									+ i));

				}

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
												.toString()));

				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(String) request
										.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
												.toString()));

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								Integer
										.toString((Integer
												.parseInt((String) request
														.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
																.toString()))) - 1));
				request.getRequestDispatcher(Jsp.STUDIE_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.BENUTZER_SUCHEN.name())) {
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
							.name())) {
				weiterleitungSystemSperrung(request, response);
			} else if (id.equals(anfrage_id.AKTION_LOGOUT.name())) {
				// Logger.getLogger(this.getClass()).fatal("Benutzer
				// ausloggen");
				loggeBenutzerAus(request, response);
				return;
			} else if (id.equals(anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name())) {
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ANZEIGEN_ADMIN
								.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_HEADER_IMPRESSUM.name())) {

				request.setAttribute("anfrage_id",
						anfrage_id.JSP_HEADER_IMPRESSUM.name());
				request.getRequestDispatcher(Jsp.IMPRESSUM).forward(request,
						response);

			} 
			// nachrichtenversand
			else if (id.equals(anfrage_id.JSP_HEADER_NACHRICHTENDIENST.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL
						.toString(), JspTitel.NACHRICHTENDIENST);
				request.getRequestDispatcher(Jsp.NACHRICHTENDIENST).forward(
						request, response);
			} else if (id.equals(anfrage_id.AKTION_NACHRICHT_VERSENDEN.name())) {
				request.getRequestDispatcher("Nachrichtendienst").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_INC_MENUE_STUDIEN_ANZEIGEN
					.name())) {
				request.setAttribute(Parameter.anfrage_id,
						StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN
								.toString());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_INC_MENUE_DATEN_AENDERN.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.DATEN_AENDERN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_INC_MENUE_STUDIENAERZTE_LISTE
					.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.STUDIENARZTE_LISTE).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_INC_MENUE_ADMIN_LISTE.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.ADMIN_LISTE).forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_INC_MENUE_PATIENT_HINZUFUEGEN
					.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.PATIENT_HINZUFUEGEN).forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_INC_MENUE_SYSTEMADMINISTRATION
					.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.SYSTEMADMINISTRATION).forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_INC_MENUE_SYSTEMSPERREN.name())) {
				// TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.SYSTEM_SPERREN).forward(
						request, response);
			
			} else if (id.equals(anfrage_id.JSP_INC_MENUE_STUDIENLEITER_ANLEGEN
					.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL
						.toString(), JspTitel.STUDIENLEITER_ANLEGEN.toString());
				
				ZentrumServlet.bindeZentrenListeAnRequest(request);
				request.getRequestDispatcher(Jsp.ADMIN_ANLEGEN).forward(
						request, response);
			} else if (id
					.equals(anfrage_id.JSP_BENUTZER_LISTE_ADMIN_ANZEIGEN_SPERREN
							.name())) {
				// TODO weiterleitung?!
				request
						.setAttribute(
								Parameter.anfrage_id,
								BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_ANZEIGEN_SPERREN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id
					.equals(StudieServlet.anfrage_id.JSP_PATIENT_HINZUFUEGEN
							.name())) {
				this.weiterleitenPatientHinzufuegenHttpServletRequest(request,
						response);
			} else if (id.equals(anfrage_id.ZENTRUM_AENDERN_SPERREN.name())) {
				// TODO weiterleitung?!
				request
						.setAttribute(
								Parameter.anfrage_id,
								ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_ZENTRUM_ANZEIGEN_SPERREN
										.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			} else if (id
					.equals(anfrage_id.JSP_BENUTZER_SPERREN_SPERREN_ENTSPERREN
							.name())) {
				request
						.setAttribute(
								Parameter.anfrage_id,
								BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_SPERREN_ENTSPERREN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_ERGEBNISSE.name())) {

				request.getRequestDispatcher(Jsp.ERGEBNISSE).forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_ERGEBNISSE_EXPORT_XLS.name())) {
				request.getRequestDispatcher("/StudieServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_ERGEBNISSE_EXPORT_CSV.name())) {
				request.getRequestDispatcher("/StudieServlet").forward(request,
						response);
			}else if(id.equals(anfrage_id.JSP_INC_MENUE_ZENRUM_ANLEGEN.name())){
				request.getRequestDispatcher(Jsp.ZENTRUM_ANLEGEN).forward(request, response);
			}

			// [end]

			// SONSTIGE WEITERLEITUNGEN
			// Schwerer Fehler: Falscher Request
			else {
				System.out
						.println("Schwerer Fehler: Falscher Request bei Dispatcher Servlet");
				System.out.println(idAttribute + " " + id);
				Logger.getLogger(this.getClass()).debug(
						"Kein Block in POST fuer die ID '" + id + "' gefunden");
				// TODO Hier muss noch entschieden werden,was passiert
				// Vorschlag: Konnte man als potentienllen Angriff werten und
				// entsprechend loggen --BTheel
			}

		}

	}// doPost

	/**
	 * 
	 * Prueft, ob die ubermittelte SessionID noch gueltig ist und ob an der
	 * Session ein Benutzer angehaengt ist.<br>
	 * Ist dies der Fall, so ist der Benutzer im System angemeldet.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @return <code>true</code>, wenn der Benutzer asngemeldet ist,
	 *         anderenfalls <code>false</code>.
	 */
	protected static boolean isBenutzerAngemeldet(HttpServletRequest request) {
		Logger.getLogger(DispatcherServlet.class).debug(
				"DispatcherServlet.isBenutzerAngemeldet()");
		boolean isSessionGueltig = request.isRequestedSessionIdValid();
		Logger.getLogger(DispatcherServlet.class).debug(
				"Pruefe: Session noch gueltg? " + isSessionGueltig);

		boolean isKontoangebunden = ((request.getSession()
				.getAttribute("aBenutzer")) != null);
		Logger.getLogger(DispatcherServlet.class).debug(
				"Pruefe: Benutzerkonto an Session gebunden? "
						+ isKontoangebunden);
		return (isSessionGueltig & isKontoangebunden);//
	}

	/**
	 * Loggt den Benutzer aus dem System aus und ruft anschlieszend die Methode
	 * {@link #weiterleitungAufIndex(HttpServletRequest, HttpServletResponse)}
	 * auf
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
	private void loggeBenutzerAus(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Logger.getLogger(this.getClass()).debug("loggeBenutzerAus()");
		LogAktion a = new LogAktion("Benutzer hat sich ausgeloggt",
				(BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer"));
		Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
		request.getSession().invalidate(); // Alte session zerstoeren
		request.getSession(); // Neue session eroeffnetn
		weiterleitungAufIndex(request, response);// Weiterleitung
	}

	/**
	 * Leitet die Anfrage auf den korrekten Index weiter<br>
	 * Ist das System gesperrt, so wird die Datei 'index_gesperrt.jsp'
	 * aufgerufen, anderenfalls die Datei 'index.jsp'
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
	private void weiterleitungAufIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Session immer killen wenn auf Indexjsp
		try {
			request.getSession(false).invalidate();
		} catch (NullPointerException e) {
			// FIXME was soll hier passieren, dhaehn, einfach nix?
		}
		if (istSystemGesperrt) {// System gesperrt
			Logger
					.getLogger(this.getClass())
					.debug(
							"System gesperrt, leite nach 'index_gesperrt.jsp' um (korrekter Ablauf) ");
			request.setAttribute(this.FEHLERNACHRICHT, meldungSystemGesperrt);
			request.getRequestDispatcher(Jsp.INDEX_GESPERRT).forward(request,
					response);
			return;
		} else {// System offen
			Logger
					.getLogger(this.getClass())
					.debug(
							"System offen, leite nach 'index.jsp' um' (korrekter Ablauf)");
			request.getRequestDispatcher(Jsp.INDEX)
					.forward(request, response);
			return;
		}
	}

	/**
	 * Leitet die Anfrage auf die Seite '/system_sperren_main.jsp' weiter und
	 * bindet die dort benoetigten Werte an den Request.
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
	private void weiterleitungSystemSperrung(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
				istSystemGesperrt);
		request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
				.toString(), meldungSystemGesperrt);
		request.getRequestDispatcher("/system_sperren_main.jsp").forward(
				request, response);
		return;

	}

	/**
	 * Leitet die Anfrage nach Authentifikation an das BenutzerServlet weiter
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
	private void weiterleitungBenutzerAnmelden(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger.getLogger(this.getClass()).debug(
				"DispatcherServlet.weiterleitungBenutzerAnmelden()");
		request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
		// request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
		// istSystemGesperrt);
		request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
				istSystemGesperrt);
		if (istSystemGesperrt) {
			// Request verliert Attr. deshalb neu setzten
			request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
					.toString(), meldungSystemGesperrt);

		}
		request.getRequestDispatcher("BenutzerServlet").forward(request,
				response);

	}

	private void weiterleitenPatientHinzufuegenHttpServletRequest(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("StudieServlet")
				.forward(request, response);

	}

	/**
	 * Liefert den Systemstatus [gesperrt|nicht gesperrt]
	 * 
	 * @return the istSystemGesperrt
	 */
	public boolean istSystemGesperrt() {
		return istSystemGesperrt;
	}

	/**
	 * Setzt den Status des Systems entsprechend des Parameters
	 * 
	 * @param istSystemGesperrt
	 *            <code>true</code> sperrt das System, <code>false</code>
	 *            entsperrt es wieder
	 */
	public void setSystemGesperrt(boolean istSystemGesperrt) {
		this.istSystemGesperrt = istSystemGesperrt;
		// Config.setSystemGesperrt(istSystemGesperrt);
		Logger.getLogger(this.getClass()).debug(
				"System gesperrt geaendert nach " + istSystemGesperrt);

	}

	/**
	 * Liefert die Meldung des Systems/Sperrungsgrund
	 * 
	 * @return Sperungsgrund
	 */
	public String getMeldungSystemGesperrt() {
		return meldungSystemGesperrt;
	}

	/**
	 * Setzt die Fehlermeldung im Dispatcher ({@link DispatcherServlet#meldungSystemGesperrt})
	 * und speichert die Meldung in der Config
	 * 
	 * @param meldungSystemGesperrt
	 *            the systemsperrungFehlermeldung to set
	 */
	public void setMeldungSystemGesperrt(String meldungSystemGesperrt) {
		System.out.println(meldungSystemGesperrt);
		this.meldungSystemGesperrt = meldungSystemGesperrt;
		// Config.setMitteilungSystemsperrung(meldungSystemGesperrt);
	}
}// DispatcherServlet
