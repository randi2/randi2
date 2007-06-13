package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.Config;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
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
		 * Benutzer klickt Benutzer registieren auf index.jsp
		 */
		JSP_INDEX_BENUTZER_REGISTRIEREN_EINS,

		JSP_ZENTRUM_ANLEGEN,

		/**
		 * Benutzer hat Disclaimer akzeptiert. (Benutzer registrieren)
		 */
		JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI,

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
		 * Benutzerdaten aendern
		 */
		JSP_DATEN_AENDERN,

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
		 * Aufforderung, einen Admin mit den gesendeten Daten anzulegen
		 */
		AKTION_ADMIN_ANLEGEN,

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
		JSP_ZENTRUM_AENDERN

	}

	/**
	 * Enhaelt die Parameternamen, die in der Session gesetzt werden koennen
	 * 
	 */
	public static enum sessionParameter {
		/**
		 * Konto des Benutzers (BenutzerkontoBean)
		 */
		A_Benutzer, // XXX Konto ist als 'aBenutzer' gebunden, nicht ueber diese
					// Kosntante

		/**
		 * Zentrum fuer das sich der Benutzer anmeldet.
		 */
		ZENTRUM_BENUTZER_ANLEGEN
	}

	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 * 
	 */
	public static enum requestParameter {

		/**
		 * Id der Anfrage an den Dispatcher
		 */
		ANFRAGE_Id("anfrage_id"),

		/**
		 * Systemstatus gesperrt[true|false] (boolean)
		 */
		IST_SYSTEM_GESPERRT("system_gesperrt"),

		/**
		 * Haelt die Begruendung der Systemsperrung (String)
		 */
		MITTEILUNG_SYSTEM_GESPERRT("mitteilung_system_gesperrt");

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
		Logger.getLogger(this.getClass()).debug("Request, Typ 'GET' empfangen");

		String id = (String) request.getParameter("anfrage_id");

		if (isBenutzerAngemeldet(request) && id != null) {
			// wurde eine Id mitgesendet _und_ der benutzer ist angemeldet
			Logger.getLogger(this.getClass()).debug("[GET]anfrage_id: " + id);
			if (id.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
					.name())) {
				weiterleitungSystemSperrung(request, response);
			} else if (id.equals(anfrage_id.AKTION_LOGOUT.name())) {
				Logger.getLogger(this.getClass()).fatal("Benutzer ausloggen");
				loggeBenutzerAus(request, response);
				return;
			}
		} else { /*
					 * keine anfrage_id empfangen oder der benutzer ist nicht
					 * angemeldet -> Weiterleitung auf den entsprechenden Index
					 */
			weiterleitungAufIndex(request, response);
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

		Logger.getLogger(this.getClass()).debug("[POST]anfrage_id: " + id);

		if (id == null) {
			/*
			 * ist keine ID gesetzt, so wird auf doGet umgeleitet Weitere Logik
			 * dort --Btheel
			 */
			doGet(request, response);
			Logger.getLogger(this.getClass()).debug(
					"Anfrage-Id == null, Anfrage nach doGet umleiten");
		}
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

			request.getRequestDispatcher("/benutzer_anlegen_eins.jsp").forward(
					request, response);

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
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);

		} else if (id
				.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN
						.name())) {
			if (!isBenutzerAngemeldet(request)) { // Benutzer nicht angemeldet
				BenutzerkontoBean anonymous = new BenutzerkontoBean();
				// FIXME FRAGE LogAktion mit String anstatt BenutzerkontoBean
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
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
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

		} else if (id.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN
				.name())) {
			if (!isBenutzerAngemeldet(request)) { // Benutzer nicht angemeldet
				BenutzerkontoBean anonymous = new BenutzerkontoBean();
				// FIXME FRAGE LogAktion mit String anstatt BenutzerkontoBean
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
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
				Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
				return;
			}
			this.setSystemGesperrt(true);

			String meldung = StringEscapeUtils.escapeHtml((String) request
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
		} else if (id.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
				.name())) {
			weiterleitungSystemSperrung(request, response);
			return;
		} else if (id.equals(anfrage_id.AKTION_ADMIN_ANLEGEN.name())) {
			Logger.getLogger(this.getClass()).debug(
					"Leite Anfrage an BenutzerServlet weiter");
			request.setAttribute("anfrage_id",
					BenutzerServlet.anfrage_id.AKTION_BENUTZER_ANLEGEN.name());
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);
		} else if (id.equals(anfrage_id.JSP_DATEN_AENDERN.name())) {
			Logger.getLogger(this.getClass()).debug(
					"Leite Anfrage an BenutzerServlet weiter");
			request.setAttribute("anfrage_id",
					BenutzerServlet.anfrage_id.BENUTZERDATEN_AENDERN.name());
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);
		} else if (id.equals(anfrage_id.JSP_ZENTRUM_AENDERN.name())) {
			Logger.getLogger(this.getClass()).debug(
					"Leite Anfrage an ZentrumServlet weiter");
			request.setAttribute("anfrage_id",
					ZentrumServlet.anfrage_id.ZENTRUM_AENDERN.name());
			request.getRequestDispatcher("ZentrumServlet").forward(request,
					response);
		}

		// [end]
		// WEITERLEITUNGEN FUER ZENTRUMSERVLET
		// [start]
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
					BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN.name());
			request.getRequestDispatcher("BenutzerServlet").forward(request,
					response);
		}

		// [end]

		// SONSTIGE WEITERLEITUNGEN
		// Schwerer Fehler: Falscher Request
		else {
			System.out.println("Scheiße");
			Logger.getLogger(this.getClass()).debug(
					"Kein Block in POST fuer die ID '" + id + "' gefunden");
			// TODO Hier muss noch entschieden werden,was passiert
			// Vorschlag: Konnte man als potentienllen Angriff werten und
			// entsprechend loggen --BTheel
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
		// TODO Auslagerung nach BenutzerServlet --Btheel
		Logger.getLogger(this.getClass()).debug(
				"DispatcherServlet.loggeBenutzerAus()");
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
		request.getSession(false).invalidate();
		if (istSystemGesperrt) {// System gesperrt
			Logger
					.getLogger(this.getClass())
					.debug(
							"System gesperrt, leite nach 'index_gesperrt.jsp' um (korrekter Ablauf) ");
			request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
					.toString(), meldungSystemGesperrt);
			request.getRequestDispatcher("index_gesperrt.jsp").forward(request,
					response);
			return;
		} else {// System offen
			Logger
					.getLogger(this.getClass())
					.debug(
							"System offen, leite nach 'index.jsp' um' (korrekter Ablauf)");
			request.getRequestDispatcher("index.jsp")
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
