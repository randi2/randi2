package de.randi2.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.exceptions.ZentrumException;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.model.fachklassen.beans.PersonBean.Titel;
import de.randi2.utility.Jsp;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.Parameter;
import de.randi2.utility.ValidierungsUtil;

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
	 * Konstante wenn nach allen Zentren gesucht werden soll
	 */
	public static final String ALLE_ZENTREN = "Alle Zentren";

	public enum aktiviertDeaktiviert {
		AKTIVIERT("aktiviert"), DEAKTIVIERT("deaktiviert"), KEINE_AUSWAHL(
				"aktiviert/deaktiviert");

		private String wert = null;

		private aktiviertDeaktiviert(String wert) {
			this.wert = wert;
		}

		@Override
		public String toString() {
			return this.wert;
		}

		public static aktiviertDeaktiviert parseTitel(String status) {

			for (aktiviertDeaktiviert aktdeakt : aktiviertDeaktiviert.values()) {
				if (status.equals(aktdeakt.toString())) {
					return aktdeakt;
				}
			}
			return null;

		}
	}

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
		CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI,

		/**
		 * Admin legt ein neues Zentrum an
		 */
		ClASS_DISPATCHERSERVLET_ZENTRUM_ANLEGEN,

		/**
		 * Zentrumsdaten aendern
		 */
		ZENTRUM_AENDERN,

//		/**
//		 * Zentrum anzeigen
//		 */
//		ZENTRUM_ANZEIGEN,

		/**
		 * Zentrum anzeigen bei Studienverwaltung
		 */
		JSP_ZENTRUM_ANZEIGEN,

		/**
		 * Daten eines einzelnen Zentrums anzeigen
		 */
		JSP_ZENTRUM_ANSEHEN,

		/**
		 * Zentrum anzeigen beim Admin.
		 */
		AKTION_ZENTRUM_ANZEIGEN_ADMIN,

		/**
		 * Admin kann sich das aktuelle Zentrum anzeigen, oder diesen sperren,
		 * entsperren.
		 */
		CLASS_DISPATCHERSERVLET_ZENTRUM_ANZEIGEN_SPERREN,

		/**
		 * Aktion um ein Zentrum einer Studie zuzuweisen
		 */
		AKTION_ZENTRUM_ZUWEISEN,

		/**
		 * Aktion um ein Zentrum einer Studie zu entziehen
		 */
		AKTION_ZENTRUM_ENTZIEHEN;
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

	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen. Hier werden Anfragen verarbeitet, die Zentren betreffen.
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

		// Benutzer registrieren
		// Schritt 2.1
		if (id
				.equals(ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI
						.name())) {
			this.classDispatcherservletBenutzerRegistrierenZwei(request,
					response);

		}
		// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
		// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
		else if (id
				.equals(ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI
						.name())) {
			this.classDispatcherservletBenutzerRegistrierenDrei(request,
					response);
		}
		// Neues Zentrum anlegen
		else if (id.equals(anfrage_id.ClASS_DISPATCHERSERVLET_ZENTRUM_ANLEGEN
				.name())) {
			this.classDispatcherservletZentrumAnlegen(request, response);

		} else if (id.equals(ZentrumServlet.anfrage_id.ZENTRUM_AENDERN.name())) {
			aendernZentrum(request, response);
		} 
//		else if (id.equals(ZentrumServlet.anfrage_id.ZENTRUM_ANZEIGEN.name())) {
//			this.classDispatcherservletZentrumAnzeigen(request, response);
//		}
		else if (id.equals(ZentrumServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN
				.name())) {
			classDispatcherservletZentrumAnzeigen(request, response);
		}else if (id
				.equals(ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ANZEIGEN_ADMIN
						.name())) {
			classDispatcherservletZentrenAnzeigenAdmin(request, response);
		}  else if (id.equals(ZentrumServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN
				.name())) {

			request.getRequestDispatcher(Jsp.ZENTRUM_ANSEHEN).forward(request,
					response);
		} else if (id
				.equals(ZentrumServlet.anfrage_id.CLASS_DISPATCHERSERVLET_ZENTRUM_ANZEIGEN_SPERREN
						.name())) {
			classDispatcherservletZentrumAnzeigenSperren(request, response);
		} else if (id.equals(ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ZUWEISEN
				.name())) {
			try {
				classDispatcherservletZentrumHinzu(request, response);
			} catch (StudieException e) {

			}
		} else if (id.equals(ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ENTZIEHEN
				.name())) {

			try {
				classDispatcherservletZentrumEntziehen(request, response);
			} catch (NumberFormatException e) {

			} catch (StudieException e) {
			}

		} else {
			// TODO Hier muss noch entschieden werden,was passiert
		}
	}

	/**
	 * Methode um ein Zentrum von einer Studie zu entfernen
	 * 
	 * @param request
	 * @param response
	 * @throws NumberFormatException
	 * @throws StudieException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void classDispatcherservletZentrumEntziehen(
			HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, StudieException, ServletException,
			IOException {

		String idx = request.getParameter(Parameter.zentrum.ZENTRUM_ID
				.toString());

		ZentrumBean aZentrum = Zentrum.getZentrum(Long.parseLong(idx));

		StudieBean aSession = (StudieBean) request.getSession().getAttribute(
				DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString());

		if (aSession != null) {
			aSession.removeZentrum(aZentrum);
			request.setAttribute(Parameter.anfrage_id.toString(),
					StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name());
		}
		request.getRequestDispatcher("StudieServlet")
				.forward(request, response);
	}

	/**
	 * Methode um ein Zentrum einer Studie zuzuordnen
	 * 
	 * @param request
	 * @param response
	 * @throws StudieException
	 */
	private void classDispatcherservletZentrumHinzu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			StudieException {
		String idx = request.getParameter(Parameter.zentrum.ZENTRUM_ID
				.toString());

		ZentrumBean aZentrum = Zentrum.getZentrum(Long.parseLong(idx));

		StudieBean aSession = (StudieBean) request.getSession().getAttribute(
				DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString());

		request.setAttribute(Parameter.anfrage_id.toString(),
				StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name());
		if (aSession != null) {

			aSession.addZentrum(aZentrum);

			request.setAttribute(Parameter.anfrage_id.toString(),
					StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name());
		}
		request.getRequestDispatcher("StudieServlet")
				.forward(request, response);
	}

	/**
	 * Funktion die ausgefuehrt wird, das Servlet von der
	 * Studienzentrenverwaltung aufgerufen wird
	 * 
	 * @param response
	 *            Requestobjekt
	 * @param request
	 *            Responseobjekt
	 * @throws IOException
	 *             Fehler bei E/A
	 * @throws ServletException
	 *             Fehler bei HTTP
	 */
	private void classDispatcherservletZentrenAnzeigenAdmin(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ZentrumBean zentrum = new ZentrumBean();
		Vector<ZentrumBean> zVec = new Vector<ZentrumBean>();
		zentrum.setFilter(true);
		if (((String) request.getParameter(Parameter.filter)) != null) {
			try {
				if (request.getParameter(Parameter.zentrum.INSTITUTION.name()) != null
						&& !request.getParameter(
								Parameter.zentrum.INSTITUTION.name()).equals(
								ZentrumServlet.ALLE_ZENTREN)) {
					zentrum
							.setInstitution(request
									.getParameter(Parameter.zentrum.INSTITUTION
											.name()));
				}
				zentrum.setAbteilung(request
						.getParameter(Parameter.zentrum.ABTEILUNGSNAME.name()));
				if (request.getParameter(Parameter.zentrum.AKTIVIERT.name())
						.equals(aktiviertDeaktiviert.DEAKTIVIERT.toString())) {
					zVec = Zentrum.suchenZentrum(zentrum);
				} else if (request.getParameter(
						Parameter.zentrum.AKTIVIERT.name()).equals(
						aktiviertDeaktiviert.KEINE_AUSWAHL.toString())) {
					zVec = Zentrum.suchenZentrum(zentrum);
					zentrum.setIstAktiviert(true);
					zVec.addAll(Zentrum.suchenZentrum(zentrum));
				} else if (request.getParameter(
						Parameter.zentrum.AKTIVIERT.name()).equals(
						aktiviertDeaktiviert.AKTIVIERT.toString())) {
					zentrum.setIstAktiviert(true);
					zVec = Zentrum.suchenZentrum(zentrum);
				}
			} catch (ZentrumException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			}
		} else {
			zVec = Zentrum.suchenZentrum(zentrum);
			zentrum.setIstAktiviert(true);
			zVec.addAll(Zentrum.suchenZentrum(zentrum));
		}
		request.setAttribute("listeZentrum", zVec);
		request.getRequestDispatcher(Jsp.ZENTRUM_ANZEIGEN_ADMIN).forward(
				request, response);
	}

	/**
	 * Funktion die ausgefuehrt wird, das Servlet von der
	 * Studienzentrenverwaltung aufgerufen wird
	 * 
	 * @param response
	 *            Requestobjekt
	 * @param request
	 *            Responseobjekt
	 * @throws IOException
	 *             Fehler bei E/A
	 * @throws ServletException
	 *             Fehler bei HTTP
	 */
	private void classDispatcherservletZentrumAnzeigen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Vector<ZentrumBean> gefilterteZentren = this.zentrenFiltern2(request,
				response);
		Vector<ZentrumBean> zugehZentren = (Vector<ZentrumBean>) request
				.getAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN.toString());
		Vector<ZentrumBean> nichtZugehZentren = (Vector<ZentrumBean>) request
				.getAttribute(StudieServlet.requestParameter.NICHT_ZUGEHOERIGE_ZENTREN.toString());
		Vector<ZentrumBean> tmp = new Vector<ZentrumBean>();

		ZentrumBean tmpElement = null;

		if (gefilterteZentren == null || gefilterteZentren.size() == 0) {
			zugehZentren.clear();
			nichtZugehZentren.clear();
			request.setAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN.toString(), zugehZentren);
			request.setAttribute(StudieServlet.requestParameter.NICHT_ZUGEHOERIGE_ZENTREN.toString(), nichtZugehZentren);

		} else {
			for (int y = 0; y < zugehZentren.size(); y++) {

				for (int i = 0; i < gefilterteZentren.size(); i++) {
					tmpElement = gefilterteZentren.elementAt(i);
					if ((zugehZentren.elementAt(y).equals(tmpElement))) {
						tmp.add(tmpElement);

					}
				}
			}
			if (tmp.size() == 0) {
				request.setAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN.toString(), tmp);
			} else {
				request.setAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN.toString() , tmp);
			}
			Vector<ZentrumBean> tmp2 = new Vector<ZentrumBean>();
			for (int y = 0; y < nichtZugehZentren.size(); y++) {

				for (int i = 0; i < gefilterteZentren.size(); i++) {
					tmpElement = gefilterteZentren.elementAt(i);
					if ((nichtZugehZentren.elementAt(y).equals(tmpElement))) {
						tmp2.add(tmpElement);
					}
				}
			}
			request.setAttribute(StudieServlet.requestParameter.NICHT_ZUGEHOERIGE_ZENTREN.toString(), tmp2);
		}
		
		request.getRequestDispatcher(Jsp.ZENTRUM_ANZEIGEN).forward(request,
				response);
	}

	/**
	 * Setzt aenderbare Daten eines vorhandenen Zentrums neu.
	 * 
	 * @param request
	 *            Requestobjekt
	 * @param response
	 *            Responseobjekt
	 * @throws IOException
	 *             Fehler bei E/A
	 * @throws ServletException
	 *             Fehler bei HTTP
	 */
	private void aendernZentrum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// wichtiger boolean
		boolean passwortGesetzt = true;

		// Alle aenderbaren Attribute des request inititalisieren
		String institution = request.getParameter(Parameter.zentrum.INSTITUTION
				.toString());
		// System.out.println(institution);
		String abteilung = request
				.getParameter(Parameter.zentrum.ABTEILUNGSNAME.toString());
		String ort = request.getParameter(Parameter.zentrum.ORT.toString());
		String plz = request.getParameter(Parameter.zentrum.PLZ.toString());
		String strasse = request.getParameter(Parameter.zentrum.STRASSE
				.toString());
		String hausnr = request.getParameter(Parameter.zentrum.HAUSNUMMER
				.toString());
		String nachname = request.getParameter(Parameter.person.NACHNAME
				.toString());
		String vorname = request.getParameter(Parameter.person.VORNAME
				.toString());
		String telefon = request.getParameter(Parameter.person.TELEFONNUMMER
				.toString());
		String fax = request.getParameter(Parameter.person.FAX.toString());
		String email = request.getParameter(Parameter.person.EMAIL.toString());
		char geschlecht = NullKonstanten.NULL_CHAR;
		if (request.getParameter(Parameter.person.GESCHLECHT.toString()) != null) {
			geschlecht = request.getParameter(
					Parameter.person.GESCHLECHT.toString()).charAt(0);
		}
		String passwort = null;

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
									.toString()))
					&& request.getParameter(
							Parameter.benutzerkonto.PASSWORT.toString())
							.length() == 12) {
				passwort = request
						.getParameter(Parameter.benutzerkonto.PASSWORT
								.toString());
				passwortGesetzt = true;
			} else {
				passwort = "";
				passwortGesetzt = false;
			}
		}
		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) (request.getSession())
				.getAttribute("aBenutzer");
		aBenutzer.setBenutzerkontoLogging(aBenutzer);
		try {
			ZentrumBean aZentrum = (ZentrumBean) request.getSession()
					.getAttribute("aZentrum");
			aZentrum.setBenutzerkontoLogging(aBenutzer);
			aZentrum.setInstitution(institution);
			aZentrum.setAbteilung(abteilung);
			aZentrum.setPlz(plz);
			aZentrum.setOrt(ort);
			aZentrum.setStrasse(strasse);
			aZentrum.setHausnr(hausnr);
			if (aZentrum.getAnsprechpartner() != null) {
				aZentrum.getAnsprechpartner()
						.setBenutzerkontoLogging(aBenutzer);
				aZentrum.getAnsprechpartner().setNachname(nachname);
				aZentrum.getAnsprechpartner().setVorname(vorname);
				aZentrum.getAnsprechpartner().setTelefonnummer(telefon);
				aZentrum.getAnsprechpartner().setFax(fax);
				aZentrum.getAnsprechpartner().setEmail(email);
				aZentrum.getAnsprechpartner().setGeschlecht(geschlecht);
				DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
						aZentrum.getAnsprechpartner());
			} else {
				PersonBean aPerson = new PersonBean();
				aPerson.setBenutzerkontoLogging(aBenutzer);
				aPerson.setNachname(nachname);
				aPerson.setVorname(vorname);
				aPerson.setTelefonnummer(telefon);
				aPerson.setFax(fax);
				aPerson.setEmail(email);
				aPerson.setGeschlecht(geschlecht);
				aPerson = DatenbankFactory.getAktuelleDBInstanz()
						.schreibenObjekt(aPerson);
				aZentrum.setAnsprechpartnerId(aPerson.getId());
			}
			if (passwort != null) {
				if (!(passwort.trim().equals(""))) {
					String hash = KryptoUtil.getInstance().hashPasswort(
							passwort);
					aZentrum.setPasswort(hash);
				}
			}
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aZentrum);
			request.setAttribute("aZentrum", aZentrum);
			if (passwortGesetzt) {
				request.setAttribute(DispatcherServlet.NACHRICHT_OK,
						"Daten erfolgreich geaendert.");
			} else {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						"Passwort wurde nicht gesetzt.\n"
								+ "Mindestens 1 Zahl und 1 Sonderzeichen.\n"
								+ "Laenge exakt 12 Zeichen.");
			}
			request.getRequestDispatcher(Jsp.ZENTRUM_AENDERN).forward(request,
					response);
		} catch (ZentrumException e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
			request.getRequestDispatcher(Jsp.ZENTRUM_AENDERN).forward(request,
					response);
		} catch (PersonException e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
			request.getRequestDispatcher(Jsp.ZENTRUM_AENDERN).forward(request,
					response);
		}
	}

	/**
	 * Methode ermittelt die Liste alle vorhandenen Zentren, setzt sie als das
	 * Attribut listeZentren, und zeigt diese dem Benutzer beim registrieren an.
	 * 
	 * @param request
	 *            Requestobjekt
	 * @param response
	 *            Responseobjekt
	 * @throws ServletException
	 *             Fehler in der Http-Verarbeitung
	 * @throws IOException
	 *             Fehler in der IO-Verarbeitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void classDispatcherservletBenutzerRegistrierenZwei(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		} catch (DatenbankExceptions e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
		}
		request.setAttribute("listeZentren", gZentrum);
		// Schritt 2.1.3
		request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(
				request, response);
	}

	protected static void bindeZentrenListeAnRequest(HttpServletRequest request)
			throws ServletException, IOException {
		ZentrumBean sZentrum = new ZentrumBean();
		sZentrum.setIstAktiviert(true);
		sZentrum.setFilter(true);
		Vector<ZentrumBean> gZentrum = null;

		try {
			gZentrum = Zentrum.suchenZentrum(sZentrum);
		} catch (DatenbankExceptions e) {
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());
		}
		request.setAttribute(DispatcherServlet.requestParameter.LISTE_ZENTREN
				.toString(), gZentrum);
	}

	/**
	 * Methode wird aufgerufen um bei der Benutzerregistrierung nach Zentren zu
	 * filtern, bzw. das Zentrumpasswort zu ueberpruefen
	 * 
	 * @param request
	 *            Requestobjekt
	 * @param response
	 *            Responseobjekt
	 * @throws ServletException
	 *             Fehler in der Http-Verarbeitung
	 * @throws IOException
	 *             Fehler in der IO-Verarbaitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void classDispatcherservletBenutzerRegistrierenDrei(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Filterung
		if (((String) request.getParameter(Parameter.filter)) != null) {
			try {
				Vector<ZentrumBean> gZentrum = null;
				if (((String) request
						.getParameter(Parameter.zentrum.INSTITUTION.name())) != ""
						&& ((String) request
								.getParameter(Parameter.zentrum.ABTEILUNGSNAME
										.name())) != "") {
					ZentrumBean sZentrum = new ZentrumBean();

					// Filter setzen
					sZentrum.setFilter(true);
					sZentrum
							.setInstitution(request
									.getParameter(Parameter.zentrum.INSTITUTION
											.name()));
					sZentrum.setAbteilung(request
							.getParameter(Parameter.zentrum.ABTEILUNGSNAME
									.name()));
					sZentrum.setIstAktiviert(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);

				} else {
					ZentrumBean sZentrum = new ZentrumBean();
					sZentrum.setFilter(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);
				}
				request.setAttribute("listeZentren", gZentrum);
			} catch (BenutzerException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			} catch (DatenbankExceptions e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			}

			request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp").forward(
					request, response);
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
					String suche = Parameter.bestaetigen + aZentrumBean.getId();
					if (request.getParameter(suche) != null) {
						Zentrum aZentrum = new Zentrum(aZentrumBean);
						// Zentrum Passwort richtig
						if (aZentrum.pruefenPasswort(request
								.getParameter(Parameter.zentrum.PASSWORT.name()
										+ aZentrumBean.getId()))) {
							// Zentrum an die Session binden
							request
									.getSession()
									.setAttribute(
											DispatcherServlet.sessionParameter.ZENTRUM_BENUTZER_ANLEGEN
													.toString(),
											aZentrum.getZentrumBean());
							request.setAttribute("aZentrum", aZentrum
									.getZentrumBean().getId());
							request.getRequestDispatcher(
									"/benutzer_anlegen_drei.jsp").forward(
									request, response);
						}
						// Zentrum Passwort falsch
						else {
							request.setAttribute("listeZentren", gZentrum);
							request.setAttribute(
									DispatcherServlet.FEHLERNACHRICHT,
									"Falsches Zentrumpasswort");
							request.getRequestDispatcher(
									"/benutzer_anlegen_zwei.jsp").forward(
									request, response);
						}
					}
				}
			} catch (DatenbankExceptions e) {
				// Fehler zurück!
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
				request.getRequestDispatcher("/benutzer_anlegen_zwei.jsp")
						.forward(request, response);
			}
		}

		// keine Filterung
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void classDispatcherservletZentrumAnlegen(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Zentrum zusammenbauen
		ZentrumBean aZentrum = new ZentrumBean();
		PersonBean aPerson = new PersonBean();
		try {
			// Zentrum setzen
			aZentrum.setInstitution(request
					.getParameter(Parameter.zentrum.INSTITUTION.name()));
			aZentrum.setAbteilung(request
					.getParameter(Parameter.zentrum.ABTEILUNGSNAME.name()));
			aZentrum.setStrasse(request.getParameter(Parameter.zentrum.STRASSE
					.name()));
			aZentrum.setHausnr(request
					.getParameter(Parameter.zentrum.HAUSNUMMER.name()));
			aZentrum.setPlz(request.getParameter(Parameter.zentrum.PLZ.name()));
			aZentrum.setOrt(request.getParameter(Parameter.zentrum.ORT.name()));
			aZentrum.setIstAktiviert(true);
			String passwort = KryptoUtil.getInstance().generatePasswort(
					KryptoUtil.ZENTRUM_PASSWORT_LAENGE);
			aZentrum.setPasswortKlartext(passwort);

			// Person setzen
			aPerson.setVorname(request.getParameter(Parameter.person.VORNAME
					.name()));
			aPerson.setNachname(request.getParameter(Parameter.person.NACHNAME
					.name()));
			// Wurde das Geschlecht eingetragen
			if (request.getParameter(Parameter.person.GESCHLECHT.name()) != null) {
				aPerson.setGeschlecht(request.getParameter(
						Parameter.person.GESCHLECHT.name()).charAt(0));
			}
			// Fehlt das Geschlecht
			else {
				throw new PersonException(PersonException.GESCHLECHT_FEHLT);
			}

			aPerson.setTelefonnummer(request
					.getParameter(Parameter.person.TELEFONNUMMER.name()));
			aPerson.setFax(request.getParameter(Parameter.person.FAX.name()));
			aPerson.setEmail(request
					.getParameter(Parameter.person.EMAIL.name()));
			// Ansprchpartner auf Kein Titel setzten
			aPerson.setTitel(Titel.KEIN_TITEL);
			// Person speichern
			aPerson.setBenutzerkontoLogging((BenutzerkontoBean) request
					.getSession().getAttribute(
							DispatcherServlet.sessionParameter.A_Benutzer
									.toString()));
			aPerson = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					aPerson);

			// Zentrum speichern
			aZentrum.setAnsprechpartnerId(aPerson.getId());
			// Logging
			aZentrum.setBenutzerkontoLogging((BenutzerkontoBean) request
					.getSession().getAttribute(
							DispatcherServlet.sessionParameter.A_Benutzer
									.toString()));
			aZentrum = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					aZentrum);
			request.setAttribute(DispatcherServlet.NACHRICHT_OK,
					"Das Zentrum: " + aZentrum.getInstitution()
							+ "wurde erfolgreich angelegt.\t" + "<br>Passwort:\t"
							+ passwort+ "<br><br><center><img src=\"images/printer-blue.gif\" onClick=\"javascript:popup();\" style=\"cursor:pointer\"></center><br><br>");

			request.getSession().setAttribute(
					DispatcherServlet.sessionParameter.PRINT_NACHRICHT
							.toString(),
					"Ein neues Zentrum wurde angelegt:<br><br><b>Zentrum:</b> "
							+ aZentrum.getInstitution() + "<br>Passwort: "
							+ passwort + "");
			request.getRequestDispatcher(Jsp.ZENTRUM_ANLEGEN).forward(request,
					response);

		} catch (BenutzerException e) {
			// Zentrum zurückschicken
			request.setAttribute(Parameter.zentrum.INSTITUTION.name(), request
					.getParameter(Parameter.zentrum.INSTITUTION.name()));
			request.setAttribute(Parameter.zentrum.ABTEILUNGSNAME.name(),
					request.getParameter(Parameter.zentrum.ABTEILUNGSNAME
							.name()));
			request.setAttribute(Parameter.zentrum.STRASSE.name(), request
					.getParameter(Parameter.zentrum.STRASSE.name()));
			request.setAttribute(Parameter.zentrum.HAUSNUMMER.name(), request
					.getParameter(Parameter.zentrum.HAUSNUMMER.name()));
			request.setAttribute(Parameter.zentrum.PLZ.name(), request
					.getParameter(Parameter.zentrum.PLZ.name()));
			request.setAttribute(Parameter.zentrum.ORT.name(), request
					.getParameter(Parameter.zentrum.ORT.name()));
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
					.getMessage());

			// Person zurückschicken
			request.setAttribute(Parameter.person.VORNAME.name(), request
					.getParameter(Parameter.person.VORNAME.name()));
			request.setAttribute(Parameter.person.NACHNAME.name(), request
					.getParameter(Parameter.person.NACHNAME.name()));
			// Falls das Geschlecht bereits gesetzt wurde wieder eintragen
			if (request.getParameter(Parameter.person.GESCHLECHT.name()) != null) {
				// System.out.println(request
				// .getParameter(Parameter.person.GESCHLECHT.name()));
				request.setAttribute(Parameter.person.GESCHLECHT.name(),
						request
								.getParameter(Parameter.person.GESCHLECHT
										.name()));
			}
			request.setAttribute(Parameter.person.TELEFONNUMMER.name(), request
					.getParameter(Parameter.person.TELEFONNUMMER.name()));
			request.setAttribute(Parameter.person.FAX.name(), request
					.getParameter(Parameter.person.FAX.name()));
			request.setAttribute(Parameter.person.EMAIL.name(), request
					.getParameter(Parameter.person.EMAIL.name()));
			request.getRequestDispatcher(Jsp.ZENTRUM_ANLEGEN).forward(request,
					response);
		}

	}

	/**
	 * Methode wird aufgerufen um bei der Benutzerregistrierung nach Zentren zu
	 * filtern, bzw. das Zentrumpasswort zu ueberpruefen
	 * 
	 * @param request
	 *            Requestobjekt
	 * @param response
	 *            Responseobjekt
	 * @throws ServletException
	 *             Fehler in der Http-Verarbeitung
	 * @throws IOException
	 *             Fehler in der IO-Verarbaitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void zentrenFiltern(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Filterung
		if (((String) request.getParameter(Parameter.filter)) != null) {
			try {
				Vector<ZentrumBean> gZentrum = null;
				if (((String) request
						.getParameter(Parameter.zentrum.INSTITUTION.toString())) != ""
						&& ((String) request
								.getParameter(Parameter.zentrum.ABTEILUNGSNAME
										.toString())) != "") {
					ZentrumBean sZentrum = new ZentrumBean();

					// Filter setzen
					sZentrum.setFilter(true);
					sZentrum.setInstitution(request
							.getParameter(Parameter.zentrum.INSTITUTION
									.toString()));
					sZentrum.setAbteilung(request
							.getParameter(Parameter.zentrum.ABTEILUNGSNAME
									.toString()));
					sZentrum.setIstAktiviert(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);

				} else {
					ZentrumBean sZentrum = new ZentrumBean();
					sZentrum.setFilter(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);
				}
				request.setAttribute("listeZentren", gZentrum);
			} catch (BenutzerException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			} catch (DatenbankExceptions e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			}

			request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN).forward(request,
					response);
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

					}
				}
			} catch (DatenbankExceptions e) {
				// Fehler zurück!
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
				request.getRequestDispatcher(Jsp.STUDIE_ANSEHEN).forward(
						request, response);
			}
		}

		// keine Filterung
	}

	/**
	 * Methode wird aufgerufen um die Zentren, die zu einer Studie hinzugefuegt
	 * werden koennen zu filtern.
	 * 
	 * @param request
	 *            Requestobjekt
	 * @param response
	 *            Responseobjekt
	 * @throws ServletException
	 *             Fehler in der Http-Verarbeitung
	 * @throws IOException
	 *             Fehler in der IO-Verarbeitung
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 * @return Vektor der gefundenen ZentrumBeans
	 */
	private Vector<ZentrumBean> zentrenFiltern2(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Filterung
		if (((String) request.getParameter(Parameter.filter)) != null) {
			try {
				Vector<ZentrumBean> gZentrum = null;
				if (((String) request
						.getParameter(Parameter.zentrum.INSTITUTION.toString())) != ""
						&& ((String) request
								.getParameter(Parameter.zentrum.ABTEILUNGSNAME
										.toString())) != "") {
					ZentrumBean sZentrum = new ZentrumBean();

					// Filter setzen
					sZentrum.setFilter(true);
					sZentrum.setInstitution(request
							.getParameter(Parameter.zentrum.INSTITUTION
									.toString()));
					sZentrum.setAbteilung(request
							.getParameter(Parameter.zentrum.ABTEILUNGSNAME
									.toString()));
					sZentrum.setIstAktiviert(true);
					gZentrum = Zentrum.suchenZentrum(sZentrum);

				}

				return gZentrum;

			} catch (BenutzerException e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			} catch (DatenbankExceptions e) {
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT, e
						.getMessage());
			}
		}
		return null;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void classDispatcherservletZentrumAnzeigenSperren(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String button = request.getParameter("button");
		StringTokenizer st = new StringTokenizer(button, "_");
		String art = st.nextToken();
		long id = Long.parseLong(st.nextToken());
		ZentrumBean aZentrum = DatenbankFactory.getAktuelleDBInstanz()
				.suchenObjektId(id, new ZentrumBean());
		request.setAttribute("aZentrum", aZentrum);
		request.getSession().setAttribute("aZentrum", aZentrum);
		if (art.equals("a")) {
			request.getRequestDispatcher(Jsp.ZENTRUM_AENDERN).forward(request,
					response);
		} else if (art.equals("s")) {
			if (aZentrum.getIstAktiviert()) {
				aZentrum.setIstAktiviert(false);
			} else {
				aZentrum.setIstAktiviert(true);
			}
			aZentrum.setFilter(true);
			BenutzerkontoBean aBenutzer = (BenutzerkontoBean) (request
					.getSession())
					.getAttribute(DispatcherServlet.sessionParameter.A_Benutzer
							.toString());
			aZentrum.setBenutzerkontoLogging(aBenutzer);

			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(aZentrum);
			classDispatcherservletZentrenAnzeigenAdmin(request, response);
		}
	}
}
