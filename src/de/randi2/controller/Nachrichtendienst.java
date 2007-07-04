package de.randi2.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import de.randi2.controller.DispatcherServlet.sessionParameter;
import de.randi2.datenbank.Datenbank;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.DatenbankSchnittstelle;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.exceptions.StudieException;
import de.randi2.model.fachklassen.Nachricht;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.Studie;
import de.randi2.model.fachklassen.Zentrum;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.model.fachklassen.beans.ZentrumBean;
import de.randi2.utility.Config;
import de.randi2.utility.Parameter;
import de.randi2.utility.SystemException;
import de.randi2.utility.Parameter.person;

/**
 * Ermoeglicht das Versenden von Nachrichten ueber das RANDI2-System.
 * 
 * Es wird aus dem Request anhand der Parameter {@link requestParameter} eine
 * Nachricht gebaut, die dann an die angegebenen Empfaenger verschickt wird. Der
 * Empfaenger wird aus einer Liste an die View ausgeliefert
 * {@link Nachrichtendienst#getEmpfaengerListe(BenutzerkontoBean)}. Hierbei
 * sind die Empfaenger wie folgt verschluesselt: {@link Identifikator} +
 * {@link Nachrichtendienst#SEPERATOR} + <ID des Beans>. Anstatt einer konkreten
 * Objekt-ID kann auch die Kosntante {@link #ALLE} verwendet werden. Sie
 * bewirkt, das die Mitteilung an alle zur Verfuegung stehenden Objekte
 * geschickt wird.
 * 
 */
public class Nachrichtendienst extends javax.servlet.http.HttpServlet {
	/**
	 * Automatisch gernerierte Serial
	 */
	private static final long serialVersionUID = 6761132208973208969L;

	/*
	 * Sehenden Auges werden die Datenbank-IDs der Beans als Identifikatoren im
	 * HTML-Quellcode stehen. Theoretisch waere es so moeglich, einen
	 * gefaelschten Request an das System zu schicken und so an beliebige Konten
	 * Nachrichten zu verschicken. Nach Absprache mit FHess entschieden, hier
	 * keine Sicherung einzubauen --BTheel:20070607
	 */
	/**
	 * Adresse des Servers
	 */
	private static String server = null;

	/**
	 * Benutzer des Servers
	 */
	private static String user = null;

	/**
	 * Passwort des Benutzers
	 */
	private static String pwd = null;

	/**
	 * Spezielle ID, die anzeigt, das die Nachricht an alle Admin sgehen soll
	 */
	private static long ALLE_ADMINS = -40l;
	
	/**
	 * Spezielle ID, die anzeigt, das die Nachricht an alle Studienleiter gehen
	 * soll
	 */
	private static long ALLE_STUDIENLEITER = -50l;
	
	/**
	 * Spezielle ID, die anzeigt, das die Nachricht an alle Zentren einer Studie
	 * gehen soll
	 */
	private static long ALLE_ZENTREN_DER_STUDIE = -60l;
	
	/**
	 * Spezielle ID, die anzeigt, das die Nachricht an alle Zentren sgehen soll
	 */
	private static long ALLE_ZENTREN = -70l;

	/**
	 * Laed die benoetigten Einstellungen aus der Config
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Nachrichtendienst() {
		getUser();
		getPwd();
		getServer();
	}

	/**
	 * Gueltige Anfrage-Ids, die an das Servlet geschickt werden koennen.<br>
	 * Das Servlet erwartet die ID in der Form, die $KONSTANTE.name() liefert.
	 */
	protected enum anfrage_id {
		/**
		 * Weist das Servlet an, aus den Parametern des Requests eine Mail zu
		 * bauen.
		 */
		AKTION_NACHRICHT_VERSENDEN
	}

	/**
	 * Stellt die Parameternamen zur Verfuegung, die das Servlet benoetigt, um
	 * eine Mail zu versenden.<br>
	 * Das Servlet erwartet die ID in der Form, die $KONSTANTE.name() liefert.
	 */
	public enum requestParameter {

		/**
		 * Feld mit dem Empfaenger der Mail
		 */
		EMPFAENGER,

		/**
		 * Feld mit dem Betreff der Mail
		 */
		BETREFF,

		/**
		 * Feld mit dem Nachrichtentext
		 */
		NACHRICHTENTEXT

	}

	/**
	 * Identifikatoren, die den Zusammenhang zwischen einer ID und einem Typ
	 * herstellt.
	 * 
	 */
	private static enum Identifikator {
		/**
		 * Nachricht an Benutzerkonto, bzw. der assoziierten Person des Kontos
		 */
		BK, ZENTRUM, ABT
	}

	/**
	 * Trennzeichen zwischen {@link Identifikator} und der Object-ID
	 */
	private static final String SEPERATOR = "#";

	/**
	 * Versucht aus einem Post-Request eine Mail zu bauen und diese zu
	 * versenden.<br>
	 * Die Methode reagiert nur auf die {@link requestParameter}
	 * {@link requestParameter#ANFRAGE_ID} {@link anfrage_id#VERSENDE_NACHRICHT}.
	 * Andere Ids bzw. nicht gesetzte werden als potentieller Angriff auf das
	 * System behandelt.<br>
	 * Alle Parameter werden auf Gueltigkeit geprueft. Entsprechen sie nicht den
	 * Konventionen, wird der Benutzer umgeleitet und bekommt eine Fehlermeldung
	 * angezeigt.<br>
	 * Nach erfolgtem Versand wird ber Benutzer auf eine ensprechende Seite mit
	 * einer Erfolgsmeldung weitergeleitet.
	 * 
	 * @param request
	 *            Resuest-Objekt
	 * @param response
	 *            Response-Objekt
	 * @throws ServletException
	 *             Siehe SuperKlasse
	 * @throws IOException
	 *             Siehe SuperKlasse
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter(Parameter.anfrage_id);

		String empfaengerString = request
				.getParameter(requestParameter.EMPFAENGER.name());

		Logger.getLogger(this.getClass()).debug(
				"Empfaenger-Code: '" + empfaengerString + "'");

		String betreff = request.getParameter(requestParameter.BETREFF.name());
		String nachrichtentext = request
				.getParameter(requestParameter.NACHRICHTENTEXT.name());

		Identifikator identifikator = null;
		long beanID = -1l;

		if ((id == null)
				|| (!id.equals(anfrage_id.AKTION_NACHRICHT_VERSENDEN.name()))
				|| !DispatcherServlet.isBenutzerAngemeldet(request)) {
			// Keine ID gesetzt, Falsche ID oder Benutzer nicht angemeldet
			// FRAGE Ausreichende Behandlung? --BTheel:20070607
			Logger.getLogger(this.getClass()).warn(
					"Illegaler Zugriff auf Nachrichtendienst von "+request.getRemoteAddr()+", User: "+request.getRemoteUser());
			return;
		}

		StringBuffer fehlermeldung = new StringBuffer();

		/*
		 * XXX Workaround waere angenehm, nachfolgende Aufrufe direkt in den
		 * Fehlercheckblock mit einzuarbeiten. Derzeit nur keine gescheite
		 * Loesung. So Redundant.
		 */
		if (empfaengerString != null) {
			empfaengerString = empfaengerString.trim();
		}
		if (betreff != null) {
			betreff = betreff.trim();
		}
		if (nachrichtentext != null) {
			nachrichtentext = nachrichtentext.trim();
		}

		// Fehlerbehandlung
		if (empfaengerString == null || empfaengerString.length() == 0
				|| empfaengerString.equals("")) {
			// Empfaenger leer
			fehlermeldung
					.append("Bitte w&auml;hlen Sie einen Empf&auml;nger<br>");
		} else {
			// Gueltige Empfaenger IDentifikation? Splitten des Strings
			String[] komponenten = empfaengerString.split(SEPERATOR);

			if (!empfaengerString.contains(SEPERATOR)
					|| komponenten.length != 2) {
				Logger.getLogger(this.getClass()).warn(
						"Aufbau des Empfaengerparameters ist illegal: "
								+ empfaengerString);
				fehlermeldung
						.append("Bitte w&auml;hlen Sie einen Empf&auml;nger<br>");
			}
			// Gueltiger Identifikator, valide BeanID
			try {
				identifikator = Identifikator.valueOf(komponenten[0]);
				beanID = Long.valueOf(komponenten[1]);
			} catch (Exception e) {
				// ist Identifikator nicht Element der Enum, fliegt ne
				// IllegalArgument
				Logger.getLogger(this.getClass()).warn(
						"Aufbau des Empfaengerparameters ist illegal: "
								+ empfaengerString);
				fehlermeldung
						.append("Bitte w&auml;hlen Sie einen Empf&auml;nger<br>");
			}
		}

		if (betreff == null || betreff.length() == 0) {
			fehlermeldung.append("Bitte geben Sie einen Betreff ein<br>");
		}
		if (nachrichtentext == null || nachrichtentext.length() == 0) {
			fehlermeldung
					.append("Bitte geben Sie einen Nachrichtentext ein<br>");
		}
		if (fehlermeldung.length() != 0) {
			// Fehlermeldung ausgeben, und zurueck
			request.setAttribute(Nachrichtendienst.requestParameter.BETREFF
					.name(), betreff);
			request.setAttribute(requestParameter.NACHRICHTENTEXT.name(),
					nachrichtentext);
			weiterleitenAufnachrichtendienstSeite(request, response, false,
					fehlermeldung.toString());

			return;
		}
		// Mail Bauen
		Nachricht mail = new Nachricht();

		mail.setDebug(true);// XXX ENtfernen --BTHEEL

		try { // Absender setzten
			mail.setAbsender(((BenutzerkontoBean) request.getSession()
					.getAttribute("aBenutzer")).getBenutzer());
		} catch (Exception e) {
			if (e instanceof PersonException) {
				/*
				 * Fliegt die Exception, dann gibts Tote. Zu einem Benutzerkonto
				 * wird das PersonBean nicht gefunden
				 */
				Logger.getLogger(this.getClass()).fatal(
						"Konnte Person zu Konto nicht finden -- Konto-ID"
								+ ((BenutzerkontoBean) request.getSession()
										.getAttribute("aBenutzer"))
										.getBenutzerId());
			}
			if (e instanceof EmailException) {
				/*
				 * Fliegt die Exception, dann gibts wieder Tote. Die E-mail
				 * Adresse ist ungueltig. Die Exception wird direkt von Java
				 * Mail durchgereicht.
				 */
				Logger.getLogger(this.getClass()).fatal(
						"Person in Konto enthaelt ungueltige Mail-Adresse -- Konto-ID "
								+ ((BenutzerkontoBean) request.getSession()
										.getAttribute("aBenutzer")));
			}
			// FIXME Fehlerbehandlung für den den Benutzer
			weiterleitenAufnachrichtendienstSeite(request, response, false,
					Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);
		}
		try { // Betreff setzten
			mail.setBetreff(betreff);
		} catch (NachrichtException e) {
			/*
			 * Die Exception fliegt, wenn betreff.length() == 0 oder
			 * betreff==null Dies wird oben bereits ausgeschlossen. Daher wird
			 * Exception hier ignoriert.
			 */
		}
		try { // Text setzten
			mail.setText(nachrichtentext);
		} catch (NachrichtException e) {
			/*
			 * Die Exception fliegt, wenn nachrichtentext.length() == 0 oder
			 * nachrichtentext==null Dies wird oben bereits ausgeschlossen.
			 * Daher wird Exception hier ignoriert.
			 */
		}
		Collection<PersonBean> liste = null;
		try {
			liste = baueEmpfaengerliste(identifikator, beanID, request);
			if (liste.size() == 0) {
				throw new NachrichtException("aellerbaetsch!");
			}
			mail.addEmpfaenger(liste);
		} catch (NachrichtException e1) {
			// empfaenger null, Filter oder Ungueltige EMailadrese Alles
			// zustände, die nicht auftreten sollten.

			request.setAttribute(requestParameter.BETREFF.name(), betreff);
			request.setAttribute(requestParameter.NACHRICHTENTEXT.name(),
					nachrichtentext);

			weiterleitenAufnachrichtendienstSeite(request, response, false,
					"Es wurden keine Empf&auml;nger gefunden");

			Logger.getLogger(this.getClass()).warn(
					"Versenden einer Mail fehlgeschlagen: " + e1.getClass()
							+ " (" + e1.getMessage() + ")");
			return;
		}
		try { // Fertige Mail versenden
			mail.senden();

		} catch (Exception e) {
			/*
			 * Senden der Mail fehlgeschlagen. Exception stammt entweder direkt
			 * aus der Sun Java Mail API oder aus Commons Email. Leere
			 * benoetigte Felder in der Mail sind ausgeschlossen, daher sind
			 * Fehler in tieferen Schichten wahrscheinlich --> Einfache Meldung
			 * an Benutzer, Exception loggen
			 */
			Logger.getLogger(this.getClass()).debug("", e);
			// FRAGE hier lieber Systemexception? -- BTheel

			weiterleitenAufnachrichtendienstSeite(request, response, false,
					Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);

			Logger.getLogger(this.getClass()).warn(
					"Versenden einer Mail fehlgeschlagen: " + e.getClass()
							+ " (" + e.getMessage() + ")");
			return;

		}

		// Weiterleiten, alles erfolgreich
		weiterleitenAufnachrichtendienstSeite(request, response, true,
				"Mitteilung erfolgreich verschickt");

		return;
	}

	/**
	 * Baut anhand eines Identifikators und einer ID die Empfaenderliste
	 * zusammen
	 * 
	 * @param identifikaktor
	 *            Identifiziert die Art der ID
	 * 
	 * @param id
	 *            DatenbankID des Objectes, Ausnahme: ist die ID
	 * @link {@link #ALLE}, so werden alle assoziierten Objekte angefuegh
	 * @param request
	 *            Ermoeglicht zugriff auf die Session
	 * @return Eine Collection mit den Persoen, die die Nachricht empfangen
	 *         empfangen sollen.
	 * @throws DatenbankExceptions
	 */
	private synchronized Collection<PersonBean> baueEmpfaengerliste(
			Identifikator identifikaktor, long id, HttpServletRequest request)
			throws SystemException {
		DatenbankSchnittstelle db = DatenbankFactory.getAktuelleDBInstanz();
		Vector<PersonBean> personen = new Vector<PersonBean>();

		if (Identifikator.BK.equals(identifikaktor)) {
			Logger.getLogger(this.getClass()).debug(
					"Suche Benutzerkonto ID: " + id);

			BenutzerkontoBean suchdummy = new BenutzerkontoBean();
			suchdummy.setFilter(true);

			if (id >= 0) {
				personen.add(Person.get(id));
			} else { // an mehere Benutzer schicken
				Vector<BenutzerkontoBean> konten = new Vector<BenutzerkontoBean>();
				if (id == ALLE_ADMINS) {
					Logger.getLogger(this.getClass())
							.debug("Suche alle Admins");
					try {
						suchdummy.setRolle(Rolle.getAdmin());
					} catch (BenutzerkontoException e) {
						// FUBAR!!!
						throw new SystemException(
								"Rolle.getAdmin() liefert unmoeglicherweise null");
					}
				} else if (id == ALLE_STUDIENLEITER) {
					Logger.getLogger(this.getClass()).debug(
							"Suche alle Studienleiter");
					try {
						suchdummy.setRolle(Rolle.getStudienleiter());
					} catch (BenutzerkontoException e) {
						// FUBAR!!!
						throw new SystemException(
								"Rolle.getStudienleiter() liefert unmoeglicherweise null");
					}
				}
				konten = DatenbankFactory.getAktuelleDBInstanz().suchenObjekt(
						suchdummy);
				for (BenutzerkontoBean konto : konten) {
					personen.add(konto.getBenutzer());
				}
			}
		} else if (Identifikator.ZENTRUM.equals(identifikaktor)) {
			Logger.getLogger(this.getClass()).debug("Suche Zentrum ID: " + id);
			Collection<BenutzerkontoBean> konten = null;
			if (id == ALLE_ZENTREN_DER_STUDIE || id == ALLE_ZENTREN) {
				// 
				StudieBean studie = (StudieBean) request
						.getSession()
						.getAttribute(
								DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
										.toString());
				// Bean an Session
				Collection<ZentrumBean> zentren;
				if (studie == null) {// wenn null, dann ist der Account
					// unabhaengig von einer Studie, ergo
					// Admin oder Sysop-> Alle Zentren
					// gewinnen
					ZentrumBean filter = new ZentrumBean();
					filter.setFilter(true);
					filter.setIstAktiviert(true);
					zentren = db.suchenObjekt(filter);
				} else { // Studiengebundener Account
					zentren = studie.getZentren();
				}
				konten = new Vector<BenutzerkontoBean>();
				for (ZentrumBean zentrum : zentren) {
					konten.addAll(zentrum.getBenutzerkonten());
				}
			} else {
				konten = (Zentrum.getZentrum(id)).getBenutzerkonten();
			}

			for (BenutzerkontoBean konto : konten) {
				Logger.getLogger(this.getClass()).debug(
						"Fuege  BK-ID hinzu: "+ konto.getBenutzer().getId());
				personen.add(konto.getBenutzer());
			}

		}
		return personen;
	}

	/**
	 * Baut anhand des uebergebenen Kontos die Empfaengerliste zusammen.<br>
	 * Methode liefert nur den Inhalt eines DropDown-Elementes.
	 * <p>
	 * Beispiel: (JSP-Code)<br>
	 * <source> &lt;select id=&quot;empfaenger&quot;
	 * name=&quot;empfaenger&quot;&gt;<br>
	 * &lt;%= Nachrichtendienst.getEmpfaengerListe(aBenutzer)%&gt;<br>
	 * &lt;/select&gt; </source><br>
	 * liefert folgendes Ergebnis:<br>
	 * <source> &lt;select id=&quot;empfaenger&quot;
	 * name=&quot;empfaenger&quot;&gt;<br>
	 * &lt;option value=&quot;&quot;&gt; -- Bitte ausw&auml;hlen --
	 * &lt;/option&gt;<br>
	 * &lt;option value=&quot;1&quot;&gt;Studienleiter&lt;/option&gt;<br>
	 * &lt;option value=&quot;2&quot;&gt;Admin&lt;/option&gt;<br>
	 * &lt;optgroup label=&quot;Mitteilung an Zentrum&quot;&gt;<br>
	 * &lt;option value=&quot;Zentrum_1&quot;&gt;Zentrum 1&lt;/option&gt;<br>
	 * &lt;option value=&quot;Zentrum_2&quot;&gt;Zentrum 2&lt;/option&gt;<br>
	 * &lt;/optgroup><br>
	 * &lt;/selec&gt; </source>
	 * </p>
	 * 
	 * @param aBenutzer
	 *            Konto des Benutzers
	 * @return Stringrepaesentation des Menues.
	 * 
	 * @throws SystemException
	 *             {@link BenutzerkontoBean#setRolle(Rolle)}
	 *             {@link Datenbank#suchenObjekt(de.randi2.datenbank.Filter)}
	 */
	public synchronized static String getEmpfaengerListe(
			HttpServletRequest request) throws SystemException {

		StringBuffer menu = new StringBuffer();
		DatenbankSchnittstelle db = DatenbankFactory.getAktuelleDBInstanz();

		BenutzerkontoBean aBenutzer = (BenutzerkontoBean) request.getSession()
				.getAttribute(sessionParameter.A_Benutzer.toString());
		StudieBean aStudie = (StudieBean) request.getSession().getAttribute(
				sessionParameter.AKTUELLE_STUDIE.toString());
		// StudieBean aStudie = Studie.getStudie(4); // XXX
		// Workaround,
		// warte
		// auf
		// Studie
		// an
		// Session
		// --Btheel
		menu
				.append("<option value=\"\"> -- Bitte ausw&auml;hlen -- </option>\n");

		// Einzelnachrichten
		if (aBenutzer.getRolle() == Rolle.getStudienarzt()) {
			sucheStudienleiter(aStudie, menu, db, false);
			sucheRolle(Rolle.getAdmin(), false, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getStudienleiter()) {
			sucheRolle(Rolle.getAdmin(), false, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getAdmin()) {
			sucheRolle(Rolle.getSysop(), false, menu, db);
			sucheRolle(Rolle.getStudienleiter(), true, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getSysop()) {
			sucheRolle(Rolle.getAdmin(), true, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getStatistiker()) {
			sucheStudienleiter(aStudie, menu, db, true);
		}
		// Gruppenmitteilungen
		if (aBenutzer.getRolle() == Rolle.getStudienleiter()) {
			sucheZentren(aStudie, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getAdmin()) {
			sucheZentren(null, menu, db);
		} else if (aBenutzer.getRolle() == Rolle.getSysop()) {
			sucheZentren(null, menu, db);
		}
		return menu.toString();
	}

	/**
	 * Fuegt alle Zentren einer Studie dem Auswahlmenue hinzu. Es wird jedes
	 * Zentrum einzelt angefuegt. Zudem wird ein Eintrag angefuegt, der die
	 * Moeglichkeit bietet, an alle Zentren eine Mitteilung zu schicken
	 * 
	 * @param studie
	 *            Studie, deren Teilnehmenden Zentren hinzugefuegt werden
	 *            sollen. Wird <code>null</code> uebergeben, so werden alle in
	 *            der Datenbank existierenden Zentren hinzugefuegt.
	 * @param menu
	 *            Stringbuffer, an den die generierten Eintraege angefuegt
	 *            werden sollen
	 * @param db
	 *            Datenbankinstanz
	 * @throws SystemException
	 *             In der Datenbank ist ein Fehler aufgetreten.
	 */
	private static void sucheZentren(StudieBean studie, StringBuffer menu,
			DatenbankSchnittstelle db) throws SystemException {
		Collection<ZentrumBean> zentren;
		if (studie == null) {
			ZentrumBean dummy = new ZentrumBean();
			dummy.setFilter(true);
			dummy.setIstAktiviert(true);
			zentren = db.suchenObjekt(dummy);
		} else {
			zentren = studie.getZentren();
		}
		if (zentren != null && zentren.size() != 0) {
			menu.append("<optgroup label=\"Mitteilung an alle Zentren\">\n");
			menu.append("\t<option value=\"" + Identifikator.ZENTRUM
					+ SEPERATOR);
			if (studie != null) {
				menu.append(+ALLE_ZENTREN_DER_STUDIE
						+ "\">Alle Zentren dieser Studie");
			} else {
				menu.append(+ALLE_ZENTREN
						+ "\">Alle aktivierten Zentren im System");
			}
			menu.append("</option>\n");
			menu.append("</optgroup>\n");
			// Einzele Zentren
			menu.append("<optgroup label=\"Mitteilung an ein Zentrum");
			if (studie != null) {
				menu.append("dieser Studie");
			}
			menu.append("\">\n");
			for (ZentrumBean zentrumBean : zentren) {
				menu.append("\t<option value=\"" + Identifikator.ZENTRUM
						+ SEPERATOR + zentrumBean.getId() + "\">");
				menu.append(zentrumBean.getInstitution());
				menu.append("</option>\n");
			}
			menu.append("</optgroup>\n");
		}
	}

	/**
	 * Sucht anhand einer uebergebenen Studie den Studienleiter und fuegt diesen
	 * Eintrag in das Dropdown mitein.
	 * 
	 * @param studie
	 *            Studie, dessen Studienleiter gesucht werden soll
	 * @param menu
	 *            Dropdownmenue mit der Empfaengerliste
	 * @param db
	 *            Datenbankverbindung
	 * @param preSelect
	 *            Wenn <code>true</code> wird der Studienleiter vorselectiert.
	 * @throws SystemException
	 *             Auftretende Datenbankfehler werden weitergeleitet
	 */
	private synchronized static void sucheStudienleiter(StudieBean studie,
			StringBuffer menu, DatenbankSchnittstelle db, boolean preSelect)
			throws SystemException {
		PersonBean dude = studie.getBenutzerkonto().getBenutzer();

		menu.append("<optgroup label=\"Mitteilung an STUDIENLEITER\">\n");
		menu.append("<option value=\"" + Identifikator.BK + SEPERATOR
				+ dude.getId() + "\"");
		if (preSelect) {
			menu.append(" selected ");
		}
		menu.append(">");
		menu.append(dude.getNachname() + ", " + dude.getVorname());
		menu.append(" ("
				+ studie.getBenutzerkonto().getZentrum().getInstitution()
				+ ", " + studie.getBenutzerkonto().getZentrum().getAbteilung()
				+ ")");
		menu.append("</option>\n");
		menu.append("</optgroup>\n");
	}

	/**
	 * Entnimmt aus der Datenbank alle Konten mit der Rolle und fuegt diese an
	 * den Stringbuffer an<br>
	 * Es wird nicht geprueft, ob das Konto berechtigt ist, Nachrichten an diese
	 * Rolle zu schicken.
	 * 
	 * @param anAlle
	 *            fuegt einen Eintrag "An alle Rolleninhaber" in das Menue mit
	 *            ein
	 * @param menu
	 *            Buffer, der das Menue generiert
	 * @param db
	 *            Instanz der Datenbank
	 * 
	 * @throws SystemException
	 *             Wenn (DummyKontoBean != Filter & Rolle == <code>null</code>)
	 *             {@link BenutzerkontoBean#setRolle(Rolle)}, Datenbankfehler
	 *             {@link Datenbank#schreibenObjekt(de.randi2.datenbank.Filter)}
	 */
	private synchronized static void sucheRolle(Rolle rolle, boolean anAlle,
			StringBuffer menu, DatenbankSchnittstelle db)
			throws SystemException {
		BenutzerkontoBean dummyBean = new BenutzerkontoBean();
		dummyBean.setFilter(true);

		try {
			dummyBean.setRolle(rolle);
		} catch (BenutzerkontoException e) {
			/*
			 * Exception wird geworfen, wenn !Filter und Rolle null, Exception
			 * kann nicht auftreten
			 */
			Logger.getLogger(Nachrichtendienst.class).debug("", e);
			throw new SystemException();
		}

		Collection<BenutzerkontoBean> result = db.suchenObjekt(dummyBean);
		if (result.size() != 0) {
			menu.append("<optgroup label=\"Mitteilung an " + rolle.getName()
					+ "\">\n");

			String key;
			ZentrumBean zentrum;

			for (BenutzerkontoBean aKonto : result) {
				key = Identifikator.BK + SEPERATOR + aKonto.getId();

				menu.append("\t<option value=\"" + key + "\">"
						+ aKonto.getBenutzer().getNachname() + ", "
						+ aKonto.getBenutzer().getVorname());
				zentrum = aKonto.getZentrum();
				menu.append(" (Zentrum: " + zentrum.getInstitution() + ", "
						+ zentrum.getAbteilung());

				if (rolle == Rolle.getStudienleiter()) {
					// Name der studie anhaengen
					Vector<StudieBean> studien = null;
					StudieBean dummy = new StudieBean();
					dummy.setFilter(true);

					try {
						dummy.setBenutzerkonto(aKonto);
					} catch (StudieException e) {
						/*
						 * Kann nicht auftreten, ID des Beans ist korrekt, da es
						 * bereits so aus der DB geholt wird, sollte trotzdem
						 * eine Exception fliegen, ist die DB inkosistent
						 */
						throw new SystemException(e.getMessage());
					}
					studien = Studie.sucheStudie(dummy);
					if (studien != null && studien.size() == 1) {
						// Benutzer leitet genau eine studie, andere Faelle
						// sollten
						// nach Systemarchitektur nicht auftreten

						menu.append(", Studie: "
								+ studien.firstElement().getName() + "");
					}

				}
				menu.append(")</option>\n");
			}
			menu.append("</optgroup>\n");
			if (anAlle) {
				menu.append("<optgroup label=\"Mitteilung an alle "
						+ rolle.getName() + "\">\n");
				menu
						.append("\t<option value=\"" + Identifikator.BK
								+ SEPERATOR);
				if (rolle == Rolle.getAdmin()) {
					menu.append(ALLE_ADMINS);
				} else if (rolle == Rolle.getStudienleiter()) {
					menu.append(ALLE_STUDIENLEITER);
				}
				menu.append("\"> Mitteilung an alle " + rolle.getName()
						+ "</option>\n");
				menu.append("</optgroup>\n");
			}

		}
	}

	/**
	 * Leitet die Anfrage an den Dispatcher mit der
	 * {@link DispatcherServlet.anfrage_id}
	 * {@link DispatcherServlet.anfrage_id#JSP_HEADER_NACHRICHTENDIENST} (als
	 * Attribut) weiter.<br>
	 * Hierbei wird die Mitteilung, die uebergeben wird, ebenfalls als Attr. an
	 * den Request gebunden.
	 * 
	 * @param erfolgreich
	 *            Wenn <code>true</code>, der vorherige Vorgang also
	 *            erfolgreich war, so wird die mitgelieferte Meldung unter
	 *            {@link DispatcherServlet#NACHRICHT_OK} angebunden,
	 *            anderenfalls unter {@link DispatcherServlet#FEHLERNACHRICHT}
	 * @param meldung
	 *            Meldung, die als Attribute an den Request gebunden wird.
	 * @param request
	 *            Resuest-Objekt
	 * @param response
	 *            Response-Objekt
	 * @throws ServletException
	 *             Siehe SuperKlasse
	 * @throws IOException
	 *             Siehe SuperKlasse
	 */
	private void weiterleitenAufnachrichtendienstSeite(
			HttpServletRequest request, HttpServletResponse response,
			boolean erfolgreich, String meldung) throws ServletException,
			IOException {
		String meldungsArt = DispatcherServlet.NACHRICHT_OK;
		if (!erfolgreich) {
			meldungsArt = DispatcherServlet.FEHLERNACHRICHT;
		}
		request.setAttribute(meldungsArt, meldung);

		request.setAttribute(Parameter.anfrage_id
				.toString(),
				DispatcherServlet.anfrage_id.JSP_HEADER_NACHRICHTENDIENST
						.name());
		request.getRequestDispatcher("DispatcherServlet").forward(request,
				response);
	}

	/**
	 * Liefert das Passwort des EMailservers
	 * 
	 * @return Das Passwort
	 */
	public static final String getPwd() {
		if (pwd == null) {
			pwd = Config.getProperty(Config.Felder.RELEASE_MAIL_PASSWORD);
		}
		return pwd;
	}

	/**
	 * Liefert die Adresse des EMailservers
	 * 
	 * @return EMailserver
	 */
	public static final String getServer() {
		if (server == null) {
			server = Config.getProperty(Config.Felder.RELEASE_MAIL_SERVER);
		}
		return server;
	}

	/**
	 * Liefert den Benutzernamen des Serversdas Passwort
	 * 
	 * @return the user
	 */
	public static final String getUser() {
		if (user == null) {
			user = Config.getProperty(Config.Felder.RELEASE_MAIL_ACCOUNT);
		}
		return user;
	}

}