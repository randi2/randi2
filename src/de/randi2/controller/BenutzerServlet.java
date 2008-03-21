package de.randi2.controller;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.DBObjekt;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.fachklassen.AutomatischeNachricht;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Recht.Rechtenamen;
import de.randi2.model.fachklassen.beans.AktivierungBean;
import de.randi2.model.fachklassen.beans.BenutzerSuchenBean;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Jsp;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.Parameter;
import de.randi2.utility.SystemException;
import de.randi2.utility.ValidierungsUtil;

/**
 * Diese Klasse repraesentiert das BENUTZERSERVLET, welches Aktionen an die
 * Benutzerkonto-Fachklasse und an den DISPATCHER weiterleitet.
 * <p>
 * Versucht sich ein Benutzer am System anzumelden, so leitet ihn der Dispatcher
 * bei entsprechender anfrage_ID ({@link DispatcherServlet.anfrage_id}) an das
 * BenutzerServlet weiter.<br>
 * Das BenutzerServlet nimmt die Anfrage an und verarbeitet diese weiter<br>
 * </p>
 * 
 * @version $Id: BenutzerServlet.java 2450 2007-05-07 13:57:04Z afreudli $
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @autor BTheel [btheel@stud.hs-heilbronn.de]
 * 
 * 
 */
@SuppressWarnings("serial")
public class BenutzerServlet extends javax.servlet.http.HttpServlet {

	/**
	 * Die Anfrage-Ids, die vom Dispatcherservlet zu verwenden sind.
	 * 
	 */
	public enum anfrage_id {
		/**
		 * Benutzer moechte sich einloggen
		 */
		CLASS_DISPATCHERSERVLET_LOGIN1,

		/**
		 * Letzter Schritt der Benutzeranmeldung, Eingabe der Persondaten
		 */
		CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER,

		/**
		 * Nutzer lässt sich ein neues Passwort zuschicken
		 */
		CLASS_DISPATCHERSERVLET_PASSWORT_VERGESSEN,

		/**
		 * Admin kann sich den aktuellen Nutzer anzeigen, oder diesen sperren,
		 * entsperren.
		 */
		CLASS_DISPATCHERSERVLET_ANZEIGEN_SPERREN,

		/**
		 * Der aktuelle Benutzer wird gesperrt, entsperrt oder das Sperren wird
		 * abgebrochen
		 */
		CLASS_DISPATCHERSERVLET_SPERREN_ENTSPERREN,

		/**
		 * Aufforderung, aus den uebergebenen Daten einen Benutzer zu generieren
		 */
		AKTION_BENUTZER_ANLEGEN,

		/**
		 * Aufforderung, aus den uebergebenen Daten einen Benutzer zu generieren
		 */
		AKTION_BENUTZER_SUCHEN,

		/**
		 * Benutzerdaten aendern
		 */
		BENUTZERDATEN_AENDERN
	}

	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public BenutzerServlet() {
		super();

	}

	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen. Hier werden Anfragen abgearbeitet, die den Benutzer betreffen.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = (String) request.getAttribute("anfrage_id");
		Logger.getLogger(this.getClass()).debug("BenutzerServlet.doPost()"); // Trace
		Logger.getLogger(this.getClass()).debug("anfrage_id: " + id);// ID
		// loggen

		// Login
		if (id.equals(BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_LOGIN1
				.name())) {
			this.classDispatcherservletLogin1(request, response);
		}// if

		// Letzter Schritt Benutzer registrieren
		else if (id
				.equals(BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER
						.name())) {
			Logger.getLogger(this.getClass()).debug(
					"id '" + id + "' korrekt erkannt");
			this.classDispatcherservletBenutzerRegistrierenVier(request,
					response);
		} else if (id.equals(anfrage_id.AKTION_BENUTZER_ANLEGEN.name())) {
			classDispatcherservletStudienleiterAdminAnlegen(request, response);
		} else if (id.equals(BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN
				.name())) {
			classDispatcherServletBenutzerSuchen(request, response);
		} else if (id.equals(BenutzerServlet.anfrage_id.BENUTZERDATEN_AENDERN
				.name())) {
			aendernBenutzer(request, response);
		} else if (id
				.equals(anfrage_id.CLASS_DISPATCHERSERVLET_PASSWORT_VERGESSEN
						.name())) {
			this.classDispatcherServletPasswortVergessen(request, response);
		} else if (id
				.equals(anfrage_id.CLASS_DISPATCHERSERVLET_ANZEIGEN_SPERREN
						.name())) {
			this.classDispatcherServletAnzeigenSperren(request, response);
		}

		else if (id
				.equals(anfrage_id.CLASS_DISPATCHERSERVLET_SPERREN_ENTSPERREN
						.name())) {
			this.classDispatcherservletSperrenEntsperren(request, response);
		}
		// if
	}// doPost

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Die Methode aendert die Benutzerdaten bei einem bereits bestehenden
	 * Benutzer in der Datenbank.
	 * 
	 * @param request
	 *            Der request fuer das Servlet.
	 * @param response
	 *            Der Response des Servlets.
	 * @throws IOException
	 *             Falls Fehler in der E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void aendernBenutzer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// wichtiger boolean
		boolean passwortGesetzt = true;

		// Alle aenderbaren Attribute des request inititalisieren
		String titel = request.getParameter(Parameter.person.TITEL.toString());
		PersonBean.Titel titelenum = null;
		String telefon = request.getParameter(Parameter.person.TELEFONNUMMER
				.toString());
		String handynummer = request.getParameter(Parameter.person.HANDYNUMMER
				.toString());
		String nachnameA = request
				.getParameter(Parameter.person.STELLVERTRETER_NACHNAME
						.toString());
		String vornameA = request
				.getParameter(Parameter.person.STELLVERTRETER_VORNAME
						.toString());
		String telefonA = request
				.getParameter(Parameter.person.STELLVERTRETER_TELEFONNUMMER
						.toString());
		char geschlechtA = NullKonstanten.NULL_CHAR;
		if (request.getParameter(Parameter.person.STELLVERTRETER_GESCHLECHT
				.toString()) != null) {
			geschlechtA = request.getParameter(
					Parameter.person.STELLVERTRETER_GESCHLECHT.toString())
					.charAt(0);
		}
		String emailA = request
				.getParameter(Parameter.person.STELLVERTRETER_EMAIL.toString());
		String fax = request.getParameter(Parameter.person.FAX.toString());
		String passwort = null;

		// Konvertierung String enum
		for (PersonBean.Titel t : PersonBean.Titel.values()) {
			if (titel.equals(t.toString())) {
				titelenum = t;
				break;
			}
		}

		// Wiederholte Passworteingabe pruefen
		if (request.getParameter(Parameter.benutzerkonto.PASSWORT.toString()) != null
				&& request
						.getParameter(Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG
								.toString()) != null) {
			if (request
					.getParameter(Parameter.benutzerkonto.PASSWORT.toString())
					.equals(
							request
									.getParameter(Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG
											.toString()))
					&& ValidierungsUtil.validierePasswortZeichen(request
							.getParameter(Parameter.benutzerkonto.PASSWORT
									.toString()))) {
				passwort = request
						.getParameter(Parameter.benutzerkonto.PASSWORT
								.toString());
				passwortGesetzt = true;
			} else {
				passwortGesetzt = false;
				passwort = "";
			}
		}

		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) (request.getSession())
				.getAttribute(DispatcherServlet.sessionParameter.A_Benutzer
						.toString());
		aBenutzer.setBenutzerkontoLogging(aBenutzer);
		try {
			PersonBean aPerson = aBenutzer.getBenutzer();
			aPerson.setBenutzerkontoLogging(aBenutzer);
			aPerson.setTitel(titelenum);
			aPerson.setTelefonnummer(telefon);
			aPerson.setHandynummer(handynummer);
			aPerson.setFax(fax);
			if (aPerson.getStellvertreter() != null) {
				aPerson.getStellvertreter().setBenutzerkontoLogging(aBenutzer);
				aPerson.getStellvertreter().setNachname(nachnameA);
				aPerson.getStellvertreter().setVorname(vornameA);
				aPerson.getStellvertreter().setTelefonnummer(telefonA);
				aPerson.getStellvertreter().setEmail(emailA);
				aPerson.getStellvertreter().setGeschlecht(geschlechtA);
			} else {
				if (nachnameA != null || vornameA != null || telefonA != null
						|| emailA != null
						|| geschlechtA != NullKonstanten.NULL_CHAR) {
					PersonBean bPerson = new PersonBean();
					bPerson.setBenutzerkontoLogging(aBenutzer);
					bPerson.setTitel(PersonBean.Titel.KEIN_TITEL);
					bPerson.setNachname(nachnameA);
					bPerson.setVorname(vornameA);
					bPerson.setTelefonnummer(telefonA);
					bPerson.setEmail(emailA);
					bPerson.setGeschlecht(geschlechtA);
					bPerson = DatenbankFactory.getAktuelleDBInstanz()
							.schreibenObjekt(bPerson);
					aPerson.setStellvertreterId(bPerson.getId());
				}
			}
			if (passwort != null) {
				if (!(passwort.trim().equals(""))) {
					String hash = KryptoUtil.getInstance().hashPasswort(
							passwort);
					aBenutzer.setPasswort(hash);
				}
			}
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aPerson);
			aBenutzer = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aBenutzer);
			request.getSession().setAttribute(
					DispatcherServlet.sessionParameter.A_Benutzer.toString(),
					aBenutzer);
			if (passwortGesetzt) {
				request.setAttribute(DispatcherServlet.NACHRICHT_OK,
						"Daten erfolgreich geaendert.");
			} else if (!passwortGesetzt) {
				request
						.setAttribute(
								DispatcherServlet.FEHLERNACHRICHT,
								"Passwort wurde nicht gesetzt.\n"
										+ "Mindestens 1 Sonderzeichen und 1 Zahl notwendig.");
			}
			request.getRequestDispatcher(Jsp.DATEN_AENDERN).forward(request,
					response);
		} catch (BenutzerException e) {
			e.printStackTrace();
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
			request.getRequestDispatcher(Jsp.DATEN_AENDERN).forward(request,
					response);
		}
	}

	/**
	 * Prueft, ob der Benutzer Sysop ist und leitet die Anfrage entsprechend
	 * weiter
	 * <p>
	 * Ist das System gesperrt (diese Pruefung findet hier nicht erneut statt!)
	 * so kann sich nur der Sysop in das System einloggen.<br>
	 * Besitzt das uebergebene Konto die Rolle des Systemoperators, so wird die
	 * Anfrage entsprechend weiter geleitet<br>
	 * Anderenfalls wird der Login-Versuch abgewiesen.<br>
	 * In beiden Faellen wird das Ergebnis geloggt.
	 * </p>
	 * 
	 * @param aBenutzer
	 *            Benutzerkonto
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
	private void weiterleitungLoginKorrektBeiSystemGesperrt(
			BenutzerkontoBean aBenutzer, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Logger.getLogger(this.getClass()).debug(
				"System gesperrt, Kandidat == Sysop?");
		if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.SYSOP) {
			weiterleitungLoginKorrekt(aBenutzer, request, response);
		} else {

			request
					.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
							" Das Systen ist derzeit gesperrt.<br> Login nicht m&ouml;glich!");
			request.getRequestDispatcher("/index_gesperrt.jsp").forward(
					request, response);
			// Ergebnis loggen
			Logger.getLogger(this.getClass()).debug(
					"System gesperrt, Benutzer abgewiesen");
			LogAktion a = new LogAktion("Benutzer abgewiesen, System gesperrt",
					aBenutzer);
			Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
		}
	}

	/**
	 * Leitet den Benutzer an die fuer ihn bestimmte Einstiegsseite in das
	 * System weiter und loggt das Ergebnis in das Anwendungslog
	 * <p>
	 * Es findet keine Pruefung statt, ob das System gesperrt ist (Vgl.
	 * {@link #weiterleitungLoginKorrektBeiSystemGesperrt(BenutzerkontoBean, HttpServletRequest, HttpServletResponse)})
	 * </p>
	 * 
	 * @param aBenutzer
	 *            Benutzerkonto anhand dessen Rolle weitergeleitet wird
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
	private void weiterleitungLoginKorrekt(BenutzerkontoBean aBenutzer,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.getLogger(this.getClass()).debug(
				"BenutzerServlet.weiterleitungLoginKorrekt()");

		(request.getSession()).setAttribute("aBenutzer", aBenutzer);// Benutzerkontobean
		// an
		// Session
		// binden
		Logger.getLogger(this.getClass()).debug("Binde Benutzer an Session");

		if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.ADMIN) {
			loggeKorrekteanmeldung(aBenutzer);
			request.setAttribute("Startseite", new Object());
			classDispatcherServletBenutzerSuchen(request, response);
			return;
		} else if (aBenutzer.getRolle().getRollenname() == Rolle.Rollen.SYSOP) {
			request.getRequestDispatcher("/systemadministration.jsp").forward(
					request, response);
			loggeKorrekteanmeldung(aBenutzer);
			return;
		} else {
			request.setAttribute(Parameter.anfrage_id.toString(),
					StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN.name());
			request.getRequestDispatcher("StudieServlet").forward(request,
					response);
			loggeKorrekteanmeldung(aBenutzer);
			return;
		}

	}

	/**
	 * Loggt die Anmeldung des uebergebenen Benutzers im Anwendungslog mit der
	 * Meldung '<i>Benutzer hat sich erfolgreich eingeloggt</i>
	 * 
	 * @param aBenutzer
	 *            neu angemeldeter Benutzer
	 */
	private void loggeKorrekteanmeldung(BenutzerkontoBean aBenutzer) {
		LogAktion a = new LogAktion("Benutzer hat sich erfolgreich eingeloggt",
				aBenutzer);
		Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
	}

	/**
	 * Weiterleitung der Anfrage bei Fehler
	 * <p>
	 * Leitet den Benutzer auf die entsprechende Indexseite weiter, abhaengig
	 * davon, ob das System gesperrt ist oder nicht.
	 * </p>
	 * <p>
	 * Die uebergebene Fehlermeldung wird an den Request als
	 * {@link DispatcherServlet#FEHLERNACHRICHT} angefuegt.
	 * </p>
	 * 
	 * @param fehlermeldungAnBenutzer
	 *            Fehlermeldung, die dem Benutzer angzeigt wird
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
	private void weiterleitungBeiFehler(String fehlermeldungAnBenutzer,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Fehlermeldung anfuegen
		request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
				fehlermeldungAnBenutzer);

		if ((Boolean) request
				.getAttribute(DispatcherServlet.requestParameter.IST_SYSTEM_GESPERRT
						.toString())) {
			request.getRequestDispatcher("/index_gesperrt.jsp").forward(
					request, response);
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
	}

	/**
	 * Realisiert den Benutzer Login.
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
	private void classDispatcherservletLogin1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger.getLogger(this.getClass()).debug(
				"BenutzerServlet.class_dispatcherservlet_login1()");
		BenutzerkontoBean sBenutzer = null;

		boolean isSystemGesperrt = (Boolean) request
				.getAttribute(DispatcherServlet.requestParameter.IST_SYSTEM_GESPERRT
						.toString());

		try {
			sBenutzer = new BenutzerkontoBean((String) request
					.getParameter(Parameter.benutzerkonto.LOGINNAME.name()),
					(String) request
							.getParameter(Parameter.benutzerkonto.PASSWORT
									.name()));
			// Filter setzen
			sBenutzer.setFilter(true);
			Vector<BenutzerkontoBean> gBenutzer = null;

			try { // Benutzer in der DB suchen
				gBenutzer = Benutzerkonto.suchenBenutzer(sBenutzer);
			} catch (DatenbankExceptions e) {
				// Interner Fehler, wird nicht an Benutzer weitergegeben
				Logger.getLogger(this.getClass()).fatal(
						"Fehler bei Benutzerfilterung", e);
			}
			if (gBenutzer.size() == 1) { // _genau_ ein Konto gefunden
				Logger
						.getLogger(this.getClass())
						.debug(
								"BenutzerServlet.class_dispatcherservlet_login1(): Genau einen Benutzer gefunden");
				// Gefundenen kontoBean aus dem Vector holen
				BenutzerkontoBean aBenutzer = gBenutzer.get(0);

				if (!aBenutzer.isGesperrt()
						&& new Benutzerkonto(aBenutzer)
								.pruefenPasswort((String) request
										.getParameter(Parameter.benutzerkonto.PASSWORT
												.name()))) {
					// Konto nicht gesperrt und PW korrekt
					if (isSystemGesperrt) {
						// Konto korrekt, aber System gesperrt
						weiterleitungLoginKorrektBeiSystemGesperrt(aBenutzer,
								request, response);
					} else {
						// Konto korrekt, normaler Ablauf
						if (aBenutzer.getLetzterLogin() == null) {
							// aBenutzer.setLetzterLogin(new
							// GregorianCalendar());
							aBenutzer.setErsterLogin(new GregorianCalendar());
							// Logging Konto setzten
							aBenutzer.setBenutzerkontoLogging(DBObjekt
									.getSystemdummy());
							// Erster Login setzten
							aBenutzer = DatenbankFactory.getAktuelleDBInstanz()
									.schreibenObjekt(aBenutzer);
						}

						weiterleitungLoginKorrekt(aBenutzer, request, response);
					}
				}// if
				else { // PW inkorrekt oder Konto gesperrt
					weiterleitungBeiFehler(
							"Loginfehler:<br> Bitte Benutzername und Passwort &uuml;berpr&uuml;fen",
							request, response);
					// Aktion loggen
					LogAktion a;
					if (aBenutzer.isGesperrt()) {
						// Konto gesperrt
						a = new LogAktion(
								"Versuchter Zugriff auf gesperrtes Konto",
								aBenutzer);
					} else {
						a = new LogAktion("Falsches Passwort eingegeben.",
								aBenutzer);
					}
					Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
				}
			}// if
			else {// Gefundene Konten != 1
				weiterleitungBeiFehler(
						"Loginfehler:<br> Bitte Benutzername und Passwort &uuml;berpr&uuml;fen",
						request, response);

				if (gBenutzer.size() == 0) {
					// Unbekanntes Konto
					LogAktion a = new LogAktion("Unbekannter Benutzername.",
							sBenutzer);
					Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
				} else { // 
					Logger.getLogger(this.getClass()).fatal(
							"Mehr als einen Benutzer fuer '"
									+ sBenutzer.getBenutzername()
									+ "' in der Datenbank gefunden!");
				}
			}// else
		} catch (BenutzerkontoException e) {
			// Ungueltiger Benutzername/Passwort
			weiterleitungBeiFehler(
					"Loginfehler:<br> Bitte Benutzername und Passwort &uuml;berpr&uuml;fen",
					request, response);

			BenutzerkontoBean dummy = new BenutzerkontoBean();
			dummy.setFilter(true);
			try {
				dummy
						.setBenutzername((String) request
								.getParameter(Parameter.benutzerkonto.LOGINNAME
										.name()));
			} catch (BenutzerkontoException e1) {
				Logger.getLogger(this.getClass()).debug("", e1);
			}

			LogAktion a = new LogAktion(
					"Benutzernamen oder Passwort eingegeben, die nicht den Konventionen von Randi2 entsprechen. Nicht eingeloggt.",
					dummy);
			Logger.getLogger(LogLayout.LOGIN_LOGOUT).warn(a);
		}// catch
	}

	/**
	 * Realisiert den letzten Schritt des Benutzerregistierens. Die Eingabe der
	 * Personendaten
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
	private void classDispatcherservletBenutzerRegistrierenVier(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Alle Attribute des request inititalisieren
		// String fehlernachricht = "";
		String vorname = request.getParameter(Parameter.person.VORNAME.name());
		String nachname = request
				.getParameter(Parameter.person.NACHNAME.name());
		char geschlecht = '\0';
		String passwort = null;
		String email = request.getParameter(Parameter.person.EMAIL.name());
		String telefon = request.getParameter(Parameter.person.TELEFONNUMMER
				.name());
		String fax = request.getParameter(Parameter.person.FAX.name());
		String handynummer = request.getParameter(Parameter.person.HANDYNUMMER
				.name());
		String titel = request.getParameter(Parameter.person.TITEL.name());
		PersonBean.Titel titelenum = null;
		PersonBean aPerson = null;
		BenutzerkontoBean aBenutzerkonto = null;
		AktivierungBean aktivierung = null;
		try {
			// Geschlecht gesetzt pruefen
			if (request.getParameter(Parameter.person.GESCHLECHT.name()) == null) {
				throw new BenutzerkontoException(
						"Bitte Geschlecht ausw&auml;hlen");
			}
			geschlecht = request.getParameter(
					Parameter.person.GESCHLECHT.name()).charAt(0);
			// Konvertierung String enum
			for (PersonBean.Titel t : PersonBean.Titel.values()) {
				if (titel.equals(t.toString())) {
					titelenum = t;
					break;
				}
			}

			// Wiederholte Passworteingabe prüfen
			if (request.getParameter(Parameter.benutzerkonto.PASSWORT.name()) != null
					&& request
							.getParameter(Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG
									.name()) != null) {
				if (request
						.getParameter(Parameter.benutzerkonto.PASSWORT.name())
						.equals(
								request
										.getParameter(Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG
												.name()))) {
					passwort = request
							.getParameter(Parameter.benutzerkonto.PASSWORT
									.name());
				} else {
					throw new BenutzerkontoException(
							BenutzerkontoException.PASSWORT_STIMMT_NICHT_UEBEREIN);

				}
			}
			// Benutzer anlegen
			// Hier findet die Überprüfung der Daten auf Serverseite statt,
			// Fehler wird an Benutzer weiter gegeben
			// Person erzeugen und in DB speichern

			aPerson = new PersonBean();
			aPerson.setNachname(nachname);
			aPerson.setVorname(vorname);
			aPerson.setTitel(titelenum);
			aPerson.setGeschlecht(geschlecht);
			aPerson.setEmail(email);
			aPerson.setTelefonnummer(telefon);
			aPerson.setHandynummer(handynummer);
			aPerson.setFax(fax);
			aPerson.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
			aPerson = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					aPerson);

			// Zugehöriges Benutzerkonto erstellen und in DB Speichern

			aBenutzerkonto = new BenutzerkontoBean(email, passwort);
			// dirty fix filter auf false
			aBenutzerkonto.setFilter(false);
			aBenutzerkonto.setBenutzer(aPerson);
			aBenutzerkonto
					.setZentrum((ZentrumBean) request
							.getSession()
							.getAttribute(
									DispatcherServlet.sessionParameter.ZENTRUM_BENUTZER_ANLEGEN
											.toString()));
			aBenutzerkonto.setRolle(Rolle.getStudienarzt());
			aBenutzerkonto.setErsterLogin(null);
			aBenutzerkonto.setLetzterLogin(null);
			aBenutzerkonto.setGesperrt(true);
			aBenutzerkonto.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
			aBenutzerkonto = Benutzerkonto.anlegenBenutzer(aBenutzerkonto)
					.getBenutzerkontobean();
			request.getRequestDispatcher("/benutzer_anlegen_vier.jsp").forward(
					request, response);

			// Aktivierung erstellen
			aktivierung = new AktivierungBean(NullKonstanten.DUMMY_ID,
					new GregorianCalendar(), aBenutzerkonto.getId(), KryptoUtil
							.getInstance().getAktivierungslink());
			aktivierung.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
			aktivierung = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aktivierung);
			AutomatischeNachricht aktivierungMail = new AutomatischeNachricht(
					aPerson, AutomatischeNachricht.autoNachricht.AKTIVIERUNG);
			aktivierungMail.senden();

			// Falls ein Fehler aufgetreten ist, request wieder auffüllen
		} catch (Exception e) {
			if (aktivierung != null
					&& aktivierung.getId() != NullKonstanten.NULL_LONG) {
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(
						aktivierung);
			}
			if (aBenutzerkonto != null
					&& aBenutzerkonto.getId() != NullKonstanten.NULL_LONG) {
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(
						aBenutzerkonto);
			}
			if (aPerson != null && aPerson.getId() != NullKonstanten.NULL_LONG) {
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(aPerson);
			}
			if (e instanceof BenutzerException) {

				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());

			} else if (e instanceof DatenbankExceptions
					&& e.getMessage().equals(
							DatenbankExceptions.TESTER_EXISTIERT_ERR)) {
				request
						.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
								"Benutzer ist bereits vorhanden. Bitte anderen Benutzernamen w&auml;hlen.");
			} else{
				throw new SystemException("Fehler bei Aktivierung");
			}

			request.getRequestDispatcher("/benutzer_anlegen_drei.jsp").forward(
					request, response);
		}

	}

	/**
	 * Methode für die Suchanfrage an die Datenbank für die Ausgabe der
	 * Benutzer.
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
	private void classDispatcherServletBenutzerSuchen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BenutzerSuchenBean benutzer = new BenutzerSuchenBean();
		Vector<BenutzerSuchenBean> benutzerVec = new Vector<BenutzerSuchenBean>();

		Rolle.Rollen aktuelleRolle = ((BenutzerkontoBean) request.getSession()
				.getAttribute(
						DispatcherServlet.sessionParameter.A_Benutzer
								.toString())).getRolle().getRollenname();

		// Studienleiter bekommt nur Studienärzte zu sehen,
		// ABER MONSTERJOIN IN DB notwendig, deshalb StudienID setzen.
		if (aktuelleRolle == Rolle.Rollen.STUDIENLEITER) {
			benutzer.setARolle(Rolle.getStudienarzt());
			benutzer.setStudienID(((StudieBean) request.getSession()
					.getAttribute(
							DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
									.toString())).getId());
		}
		// Sysop bekommt nur Admins zu sehen
		else if (aktuelleRolle == Rolle.Rollen.SYSOP) {
			benutzer.setARolle(Rolle.getAdmin());
		}
		else if (aktuelleRolle==Rolle.Rollen.ADMIN)
		{
			benutzer.setARolle(Rolle.getStudienarzt());
		}
		// Kein Global Welcome Admin
		if (request.getAttribute("Startseite") == null) {
			benutzer.setNachname(request.getParameter(Parameter.person.NACHNAME
					.name()));
			benutzer.setVorname(request.getParameter(Parameter.person.VORNAME
					.name()));
			benutzer.setEmail(request.getParameter(Parameter.person.EMAIL
					.name()));
			benutzer.setLoginname(request
					.getParameter(Parameter.benutzerkonto.LOGINNAME.name()));
			if (request.getParameter(Parameter.zentrum.INSTITUTION.name()) != null
					&& !request.getParameter(
							Parameter.zentrum.INSTITUTION.name()).equals(
							ZentrumServlet.ALLE_ZENTREN)) {
				benutzer.setInstitut(request
						.getParameter(Parameter.zentrum.INSTITUTION.name()));
			}
		}
		benutzer.setFilter(true);
		benutzerVec = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
				benutzer);
		//Falls die Rolle ADMIN ist müssen noch die SLs dazu
		if (aktuelleRolle==Rolle.Rollen.ADMIN){
			benutzer.setARolle(Rolle.getStudienleiter());
			if (request.getAttribute("Startseite") == null) {
				benutzer.setNachname(request.getParameter(Parameter.person.NACHNAME
						.name()));
				benutzer.setVorname(request.getParameter(Parameter.person.VORNAME
						.name()));
				benutzer.setEmail(request.getParameter(Parameter.person.EMAIL
						.name()));
				benutzer.setLoginname(request
						.getParameter(Parameter.benutzerkonto.LOGINNAME.name()));
				if (request.getParameter(Parameter.zentrum.INSTITUTION.name()) != null
						&& !request.getParameter(
								Parameter.zentrum.INSTITUTION.name()).equals(
								ZentrumServlet.ALLE_ZENTREN)) {
					benutzer.setInstitut(request
							.getParameter(Parameter.zentrum.INSTITUTION.name()));
				}
			}
			benutzer.setFilter(true);
			benutzerVec.addAll(DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
					benutzer));
		}
		request.setAttribute("listeBenutzer", benutzerVec);
		request.getRequestDispatcher(Jsp.BENUTZER_LISTE_ADMIN).forward(request,
				response);

	}

	/**
	 * Methode zum Erstellen eines Studienleiteraccounts
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void classDispatcherservletStudienleiterAdminAnlegen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Alle Attribute des request inititalisieren
		
	
		PersonBean aPerson = null;
		BenutzerkontoBean aBenutzerkonto=null;
		AktivierungBean aktivierung=null;
		
		String vorname = request.getParameter(Parameter.person.VORNAME.name());
		String nachname = request
				.getParameter(Parameter.person.NACHNAME.name());
		String geschlecht_tmp = request
				.getParameter(Parameter.person.GESCHLECHT.name());
		char geschlecht = '\0';
		String passwort = KryptoUtil.getInstance().generatePasswort(10);
		String email = request.getParameter(Parameter.person.EMAIL.name());
		String telefon = request.getParameter(Parameter.person.TELEFONNUMMER
				.name());
		String fax = request.getParameter(Parameter.person.FAX.name());

		String institut = request
				.getParameter(Parameter.benutzerkonto.ZENTRUM_FK.name());
		String titel = request.getParameter(Parameter.person.TITEL.name());
		PersonBean.Titel titelenum = null;
		String vornameA = request
				.getParameter(Parameter.person.STELLVERTRETER_VORNAME.name());
		String nachnameA = request
				.getParameter(Parameter.person.STELLVERTRETER_NACHNAME.name());
		String telefonA = request
				.getParameter(Parameter.person.STELLVERTRETER_TELEFONNUMMER
						.name());
		String emailA = request
				.getParameter(Parameter.person.STELLVERTRETER_EMAIL.name());
		String titelA = request
				.getParameter(Parameter.person.STELLVERTRETER_TITEL.name());
		String geschlechtA_tmp = request
				.getParameter(Parameter.person.STELLVERTRETER_GESCHLECHT.name());
		char geschlechtA = '\0';
		PersonBean.Titel titelAenum = null;
		String benutzername = request
				.getParameter(Parameter.benutzerkonto.LOGINNAME.name());
		Long zentrumId = null;

		Rolle rolle = ((BenutzerkontoBean) request.getSession().getAttribute(
				DispatcherServlet.sessionParameter.A_Benutzer.toString())).getRolle();
		try {
	
			// Konvertierung String enum
			for (PersonBean.Titel t : PersonBean.Titel.values()) {
				if (titel.equals(t.toString())) {
					titelenum = t;
					break;
				}
			}
			// Konvertierung String enumStellvertreter

			for (PersonBean.Titel tA : PersonBean.Titel.values()) {
				if (titelA.equals(tA.toString())) {
					titelAenum = tA;
					break;
				}
			}
			// Geschlecht gesetzt pruefen
			if (geschlecht_tmp == null || geschlecht_tmp.length() == 0) {
				throw new Exception(
						"Geschlecht des Accountinhabers nicht gesetzt");
			}
			// Geschlecht setzten
			geschlecht = geschlecht_tmp.charAt(0);

			// GeschlechtA gesetzt pruefen
			if (geschlechtA_tmp == null || geschlechtA_tmp.length() == 0) {
				throw new Exception(
						"Geschlecht des Stellvertreters nicht gesetzt");
			}
			// Geschlecht setzten
			geschlechtA = geschlechtA_tmp.charAt(0);

			// Konto des Benutzers, der die neuen Accounts anlegt
			BenutzerkontoBean anleger = (BenutzerkontoBean) request
					.getSession().getAttribute("aBenutzer");

						
			// Benutzer(Person) anlegen
			aPerson = new PersonBean();
			aPerson.setNachname(nachname);
			aPerson.setVorname(vorname);
			aPerson.setTitel(titelenum);
			aPerson.setGeschlecht(geschlecht);
			aPerson.setEmail(email);
			aPerson.setTelefonnummer(telefon);
			aPerson.setFax(fax);
			aPerson.setBenutzerkontoLogging(anleger);

			// Stellvertreter basteln
			PersonBean rolf = new PersonBean();
			rolf.setNachname(nachnameA);
			rolf.setGeschlecht(geschlechtA);
			rolf.setVorname(vornameA);
			rolf.setTelefonnummer(telefonA);
			rolf.setEmail(emailA);
			rolf.setTitel(titelAenum);
			rolf.setBenutzerkontoLogging(anleger);

			// Konto anlegen
			aBenutzerkonto = new BenutzerkontoBean();
			aBenutzerkonto.setBenutzername(benutzername);
			aBenutzerkonto.setPasswort(KryptoUtil.getInstance().hashPasswort(
					passwort));

			zentrumId = Long.valueOf(institut);
			aBenutzerkonto.setZentrumId(zentrumId);
			
			if (rolle.besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN)) {
				aBenutzerkonto.setRolle(Rolle.getStudienleiter());
			}
			if (rolle.besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN)) {
				aBenutzerkonto.setRolle(Rolle.getAdmin());
			}

			aBenutzerkonto.setErsterLogin(null);
			aBenutzerkonto.setLetzterLogin(null);
			aBenutzerkonto.setGesperrt(true);
			aBenutzerkonto.setBenutzerkontoLogging(anleger);

			// Alles wegspeichern
			rolf = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(rolf);

			aPerson.setStellvertreter(rolf);

			aPerson = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					aPerson);

			aBenutzerkonto.setBenutzer(aPerson);
			Benutzerkonto konto = Benutzerkonto.anlegenBenutzer(aBenutzerkonto);

			// Mitteilungsversand
			// Passwort

			AutomatischeNachricht passwortmail = new AutomatischeNachricht(
					aBenutzerkonto.getBenutzer(), passwort,
					AutomatischeNachricht.autoNachricht.PASSWORT_SL_ADMIN);
			passwortmail.senden();

			// Aktivierungsmail
			aktivierung = new AktivierungBean();
			aktivierung.setBenutzerkonto(konto.getBenutzerkontobean());
			aktivierung.setAktivierungsLink(KryptoUtil.getInstance()
					.getAktivierungslink());
			aktivierung.setVersanddatum(new GregorianCalendar());
			aktivierung.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
			aktivierung = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aktivierung);

			AutomatischeNachricht aktivierungMail = new AutomatischeNachricht(
					konto.getBenutzerkontobean().getBenutzer(),
					AutomatischeNachricht.autoNachricht.AKTIVIERUNG);
			aktivierungMail.senden();

			
			request
					.setAttribute(
							DispatcherServlet.NACHRICHT_OK,
							"Account erfolgreich angelegt.<br> Das Passwort und der Aktivierungslink wurden per E-Mail verschickt.");
			
			if (rolle.besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN)) {
				request.setAttribute(Parameter.anfrage_id.toString(),
						DispatcherServlet.anfrage_id.JSP_INC_MENUE_STUDIENLEITER_ANLEGEN.name());
			}
			if (rolle.besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN)) {
				request.setAttribute(Parameter.anfrage_id.toString(),
						DispatcherServlet.anfrage_id.JSP_INC_MENUE_ADMIN_ANLEGEN.name());
			}
			
			
			request.getRequestDispatcher("/DispatcherServlet").forward(request,
					response);

		} catch (Exception e) {
		
			if(aktivierung!=null&&aktivierung.getId()!=NullKonstanten.NULL_LONG){
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(aktivierung);
			}
			if(aBenutzerkonto!=null&&aBenutzerkonto.getId()!=NullKonstanten.NULL_LONG){
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(aBenutzerkonto);
			}
			if(aPerson!=null&&aPerson.getId()!=NullKonstanten.NULL_LONG){
				DatenbankFactory.getAktuelleDBInstanz().loeschenObjekt(aPerson);
			}

		

			request.setAttribute(Parameter.person.VORNAME.name(), vorname);
			request.setAttribute(Parameter.person.NACHNAME.name(), nachname);

			request.setAttribute(Parameter.person.EMAIL.name(), email);
			request
					.setAttribute(Parameter.person.TELEFONNUMMER.name(),
							telefon);
			request.setAttribute(Parameter.person.FAX.name(), fax);

			request.setAttribute(Parameter.benutzerkonto.ZENTRUM_FK.name(),
					Long.valueOf(institut));
			request.setAttribute(Parameter.person.TITEL.name(), titelenum
					.toString());
			request.setAttribute(
					Parameter.person.STELLVERTRETER_VORNAME.name(), vornameA);
			request.setAttribute(Parameter.person.STELLVERTRETER_NACHNAME
					.name(), nachnameA);
			request.setAttribute(Parameter.person.STELLVERTRETER_TELEFONNUMMER
					.name(), telefonA);
			request.setAttribute(Parameter.benutzerkonto.LOGINNAME.name(),
					benutzername);
			request.setAttribute(Parameter.person.STELLVERTRETER_EMAIL.name(),
					emailA);
			request.setAttribute(Parameter.person.STELLVERTRETER_TITEL.name(),
					titelAenum.toString());

			if (geschlecht == 'm') {
				request.setAttribute(Parameter.person.GESCHLECHT.name(), "m");

			} else if (geschlecht == 'w') {
				request.setAttribute(Parameter.person.GESCHLECHT.name(), "w");
			}

			if (geschlechtA == 'm') {
				request.setAttribute(Parameter.person.STELLVERTRETER_GESCHLECHT
						.name(), "m");

			} else if (geschlechtA == 'w') {
				request.setAttribute(Parameter.person.STELLVERTRETER_GESCHLECHT
						.name(), "w");
			}

			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());

			if (rolle.besitzenRolleRecht(Rechtenamen.STULEIACCOUNTS_VERWALTEN)) {
				request.setAttribute(Parameter.anfrage_id.toString(),
						DispatcherServlet.anfrage_id.JSP_INC_MENUE_STUDIENLEITER_ANLEGEN.name());
			}
			if (rolle.besitzenRolleRecht(Rechtenamen.ADMINACCOUNTS_VERWALTEN)) {
				request.setAttribute(Parameter.anfrage_id.toString(),
						DispatcherServlet.anfrage_id.JSP_INC_MENUE_ADMIN_ANLEGEN.name());
			}
			request.getRequestDispatcher("/DispatcherServlet").forward(request,
					response);
		}
	}

	/**
	 * Methode zum Zusenden eines neuen Passworts an den Studienarzt
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void classDispatcherServletPasswortVergessen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean fehler = false;

		// Benutzerkonto auslesen
		String benutzername = request
				.getParameter(Parameter.benutzerkonto.LOGINNAME.name());
		BenutzerkontoBean aBenutzerkonto = new BenutzerkontoBean();
		try {
			aBenutzerkonto.setBenutzername(benutzername);
			aBenutzerkonto.setFilter(true);
			aBenutzerkonto = DatenbankFactory.getAktuelleDBInstanz()
					.suchenObjekt(aBenutzerkonto).firstElement();

			// Neues Passwort generieren und reinschreiben;
			String neuesPasswort = KryptoUtil.getInstance()
					.generatePasswort(12);

			aBenutzerkonto.setPasswort(KryptoUtil.getInstance().hashPasswort(
					neuesPasswort));
			aBenutzerkonto.setBenutzerkontoLogging(DBObjekt.getSystemdummy());
			aBenutzerkonto = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aBenutzerkonto);

			AutomatischeNachricht autoNachricht = new AutomatischeNachricht(
					aBenutzerkonto.getBenutzer(), neuesPasswort,
					AutomatischeNachricht.autoNachricht.NEUES_PASSWORT);
			Logger.getLogger(this.getClass()).debug(
					"Neues Passwort ist:\t" + neuesPasswort);

			autoNachricht.senden();

			// Am Benutzernamen ist was falsch
		} catch (BenutzerkontoException e) {
			fehler = true;
		}
		// Nutzer ist nicht in DB
		catch (NoSuchElementException e) {
			fehler = true;
		} catch (NachrichtException e) {
			fehler = true;
		}

		// Logging
		if (fehler) {
			Logger.getLogger(this.getClass()).info(
					"Neue Passwort fuer:\t" + benutzername
							+ " wurde NICHT versandt.");

		} else {
			Logger.getLogger(this.getClass()).info(
					"Neue Passwort fuer:\t" + benutzername
							+ "  WURDE versandt.");
		}
		// Es kommt immer OK Nachricht, da Sicherheitsrelevant
		request
				.setAttribute(
						DispatcherServlet.NACHRICHT_OK,
						"Sollte dieser Loginname im System vorhanden sein, so erhalten Sie in Kürze eine Email mit einem neuen Passwort");
		request.getRequestDispatcher(Jsp.PASSWORT_VERGESSEN).forward(request,
				response);

	}

	/**
	 * Methode die entscheidet welcher Benutzer aus der Benutzerliste angezeigt bzw. EntSperrt wird.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void classDispatcherServletAnzeigenSperren(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String button = request.getParameter("button");
		StringTokenizer st = new StringTokenizer(button, "_");
		String art = st.nextToken();
		long id = Long.parseLong(st.nextToken());
		BenutzerkontoBean aBenutzer = DatenbankFactory.getAktuelleDBInstanz()
				.suchenObjektId(id, new BenutzerkontoBean());
		if (art.equals("a")) {
			request.setAttribute("aBenutzer", aBenutzer);
			request.getRequestDispatcher(Jsp.BENUTZER_ANZEIGEN_ADMIN).forward(
					request, response);
		} else if (art.equals("s")) {
			request
					.getSession()
					.setAttribute(
							DispatcherServlet.sessionParameter.BENUTZER_SPERREN_ENTSPERREN_ADMIN
									.toString(), aBenutzer);
			request.getRequestDispatcher(Jsp.BENUTZER_SPERREN).forward(request,
					response);
		}
	}

	/**
	 * Ein Benutzer wird gesperrt oder entsperrt.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 */
	private void classDispatcherservletSperrenEntsperren(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Es wird gesperrt oder entsperrt

		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) request
				.getSession()
				.getAttribute(
						DispatcherServlet.sessionParameter.BENUTZER_SPERREN_ENTSPERREN_ADMIN
								.toString());
		aBenutzer.setBenutzerkontoLogging((BenutzerkontoBean) request
				.getSession().getAttribute(
						DispatcherServlet.sessionParameter.A_Benutzer
								.toString()));
		// Achtung es wurde keine Nachricht eingegebn
		String nachricht = request
				.getParameter(Parameter.benutzerkonto.NACHRICHT.toString());
		if (nachricht == null) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
					"Bitte geben Sie einen Text ein.");
			request.getRequestDispatcher(Jsp.BENUTZER_SPERREN).forward(request,
					response);
			return;
		}
		try {
			// Benutzer wird entsperrt
			if (aBenutzer.isGesperrt()) {

				aBenutzer.setGesperrt(false);
				AutomatischeNachricht aNachricht = new AutomatischeNachricht(
						aBenutzer.getBenutzer(), nachricht,
						AutomatischeNachricht.autoNachricht.BENUTZER_ENTSPERREN);

				aNachricht.senden();

			}
			// Benutzer wird gesperrt
			else {
				aBenutzer.setGesperrt(true);
				AutomatischeNachricht aNachricht = new AutomatischeNachricht(
						aBenutzer.getBenutzer(), nachricht,
						AutomatischeNachricht.autoNachricht.BENUTZER_SPERREN);
				aNachricht.senden();
			}
			// Benutzer schreiben
			aBenutzer.setBenutzerkontoLogging((BenutzerkontoBean) request
					.getSession().getAttribute(
							DispatcherServlet.sessionParameter.A_Benutzer
									.toString()));
			aBenutzer = DatenbankFactory.getAktuelleDBInstanz()
					.schreibenObjekt(aBenutzer);
			request.setAttribute(DispatcherServlet.NACHRICHT_OK,
					"Änderung wurde erfolgeich durchgef&uuml;hrt");
			request.getRequestDispatcher(Jsp.BENUTZER_SPERREN).forward(request,
					response);
			return;

		} catch (NachrichtException e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
					"Achtung Nachricht wurde nicht versand");
			request.getRequestDispatcher(Jsp.BENUTZER_SPERREN).forward(request,
					response);
			return;

		}

	}

}