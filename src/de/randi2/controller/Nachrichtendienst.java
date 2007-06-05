package de.randi2.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import de.randi2.datenbank.Datenbank;
import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.DatenbankSchnittstelle;
import de.randi2.datenbank.Filter;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.BenutzerkontoException;
import de.randi2.model.exceptions.NachrichtException;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Benutzerkonto;
import de.randi2.model.fachklassen.Nachricht;
import de.randi2.model.fachklassen.Rolle;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Config;
import de.randi2.utility.KryptoUtil;
import de.randi2.utility.SystemException;

/**
 * Servlet implementation class for Servlet: Nachrichtendienst
 * 
 */
@SuppressWarnings("serial")
public class Nachrichtendienst extends javax.servlet.http.HttpServlet {

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
	public enum anfrage_id {
		/**
		 * Weist das Servlet an, aus den Parametern des Requests eine Mail zu
		 * bauen.
		 */
		VERSENDE_NACHRICHT
	}

	/**
	 * Stellt die Parameternamen zur Verfuegung, die das Servlet benoetigt, um
	 * eine Mail zu versenden.<br>
	 * Das Servlet erwartet die ID in der Form, die $KONSTANTE.name() liefert.
	 */
	public enum requestParameter {
		/**
		 * Anfrage-Id {@link anfrage_id#VERSENDE_NACHRICHT} ist die einzige ID,
		 * auf die das Servlet reagiert und nicht als potentiellen Angriff
		 * wertet.
		 */
		ANFRAGE_ID,

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

	public static enum sessionAttribute {

		EMPFAENGERLISTE
	}

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
		String id = request.getParameter(requestParameter.ANFRAGE_ID.name());
		String empfaenger = request.getParameter(requestParameter.EMPFAENGER
				.name());

		String betreff = request.getParameter(requestParameter.BETREFF.name());
		betreff = betreff.trim();
		String nachrichtentext = request
				.getParameter(requestParameter.NACHRICHTENTEXT.name());
		nachrichtentext = nachrichtentext.trim();
		StringBuffer fehlermeldung = new StringBuffer();

		if ((id != null) && (id.equals(anfrage_id.VERSENDE_NACHRICHT.name()))) {

			// TODO Benutzer angemeldet?

			// fehlerbehandlung
			if (empfaenger == null || empfaenger.length() == 0
					|| empfaenger.equals("") || empfaenger.length()!=64) {
				// FIXME Vorlaufige Behandlung, Format steht nicht endgueltig
				// fest--BTheel
				fehlermeldung
						.append("Bitte w&auml;hlen Sie einen Empf&auml;nger<br>");
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
				// empfaenger zurueckgeben?!
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						fehlermeldung.toString());
				request.getRequestDispatcher("nachrichtendienst.jsp").forward(
						request, response);
				return;
			}
			// Mail Bauen
			Nachricht mail = new Nachricht();

			try { // Absender setzten
				mail.setAbsender(((BenutzerkontoBean) request.getSession()
						.getAttribute("aBenutzer")).getBenutzer());
			} catch (Exception e) {
				if (e instanceof PersonException) {
					/*
					 * Fliegt die Exception, dann gibts Tote. Zu einem
					 * Benutzerkonto wird das PersonBean nicht gefunden
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
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);
				request.getRequestDispatcher("nachrichtendienst.jsp").forward(
						request, response);
			}
			try { // Betreff setzten
				mail.setBetreff(betreff);
			} catch (NachrichtException e) {
				/*
				 * Die Exception fliegt, wenn betreff.length() == 0 oder
				 * betreff==null Dies wird oben bereits ausgeschlossen. Daher
				 * wird Exception hier ignoriert.
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
			// Empfaenger setzten
			// der Empfaender ist ueber einen hashCode verschluesselt in einer
			// map gespeichert, die an der Session haengt.
			Hashtable<String, Filter> map = (Hashtable<String, Filter>) request
					.getSession().getAttribute(
							sessionAttribute.EMPFAENGERLISTE.name());
			
			if (map ==null || !map.containsKey(empfaenger)){
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);
				request.getRequestDispatcher("nachrichtendienst.jsp").forward(
						request, response);
				Logger.getLogger(this.getClass()).warn(
						"Versenden einer Mail fehlgeschlagen: Empfaengerliste war nicht an Session gebunden");
				return;
			}
			 //Hier weiter
			
			try {
				mail.addEmpfaenger((PersonBean) null);
			} catch (NachrichtException e1) {
				// empfaenger null, Filter oder Ungueltige EMailadrese Alles
				// zustände, die nicht auftreten sollten.
				e1.printStackTrace(); // XXX entfernen
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);
				request.getRequestDispatcher("nachrichtendienst.jsp").forward(
						request, response);
				Logger.getLogger(this.getClass()).warn(
						"Versenden einer Mail fehlgeschlagen: " + e1.getClass()
								+ " (" + e1.getMessage() + ")");
			}
			try { // Fertige Mail versenden
				mail.senden();
			} catch (Exception e) {
				/*
				 * Senden der Mail fehlgeschlagen. Exception stammt entweder
				 * direkt aus der Sun Java Mail API oder aus Commons Email.
				 * Leere benoetigte Felder in der Mail sind ausgeschlossen,
				 * daher sind Fehler in tieferen Schichten wahrscheinlich -->
				 * Einfache Meldung an Benutzer, Exception loggen
				 */
				e.printStackTrace(); // XXX entfernen
				// FRAGE hier lieber Systemexception? -- BTheel
				request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,
						Nachricht.NACHRICHTENVERSAND_FEHLGESCHLAGEN);
				request.getRequestDispatcher("nachrichtendienst.jsp").forward(
						request, response);
				Logger.getLogger(this.getClass()).warn(
						"Versenden einer Mail fehlgeschlagen: " + e.getClass()
								+ " (" + e.getMessage() + ")");

			}
			// Attris setzten fuer Folgeseite
			request.setAttribute(requestParameter.EMPFAENGER.name(), "arg1");
			request.setAttribute(requestParameter.BETREFF.name(), betreff);
			request.setAttribute(requestParameter.NACHRICHTENTEXT.name(),
					nachrichtentext);
			// Weiterleiten
			request.getRequestDispatcher("nachricht_verschickt.jsp").forward(
					request, response);
			return;
		}
		System.out.println("Shyce!");
		// TODO warn in den Anwendungslog, potentieller Angriff --Btheel
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
	public static String getEmpfaengerListe(BenutzerkontoBean aBenutzer,
			HttpSession session) throws SystemException {
		StringBuffer menu = new StringBuffer();
		DatenbankSchnittstelle db = DatenbankFactory.getAktuelleDBInstanz();

		Map<String, Filter> empfaengerListe = new Hashtable<String, Filter>();

		// TODO FIXME Der Studienleiter kann aus der Auswahl am Anfang gewonnen
		// werden. Dies ist noch zu implementieren.

		menu
				.append("<option value=\"\"> -- Bitte ausw&auml;hlen -- </option>\n");

		if (aBenutzer.getRolle() == Rolle.getAdmin()) {
			sucheRolle(Rolle.getSysop(), menu, db, empfaengerListe);
			sucheRolle(Rolle.getAdmin(), menu, db, empfaengerListe);
		}

		session.setAttribute(sessionAttribute.EMPFAENGERLISTE.name(),
				empfaengerListe);

		return menu.toString();
	}

	/**
	 * Entnimmt aus der Datenbank alle Konten mit der Rolle und fuegt diese an
	 * den Stringbuffer an
	 * 
	 * @param menu
	 *            Buffer, der das Menue generiert
	 * @param db
	 *            Instanz der Datenbank
	 * @throws SystemException
	 *             Wenn (DummyKontoBean != Filter & Rolle == <code>null</code>)
	 *             {@link BenutzerkontoBean#setRolle(Rolle)}, Datenbankfehler
	 *             {@link Datenbank#schreibenObjekt(de.randi2.datenbank.Filter)}
	 */
	private static void sucheRolle(Rolle rolle, StringBuffer menu,
			DatenbankSchnittstelle db, Map<String, Filter> map)
			throws SystemException {

		BenutzerkontoBean dummyBean = new BenutzerkontoBean();
		dummyBean.setFilter(true);

		try {
			dummyBean.setRolle(rolle);
		} catch (BenutzerkontoException e) {
			/*
			 * XXX Exception wird geworfen, wenn !Filter und Rolle null,
			 * Exception kann nicht auftreten
			 */
			e.printStackTrace();
			throw new SystemException();
		}
		try {
			Collection<BenutzerkontoBean> result = db.suchenObjekt(dummyBean);
			System.out.println(result.toString());
			if (result.size() != 0) {
				menu.append("<optgroup label=\"Mitteilung an "
						+ rolle.getName() + "\">\n");

				String key;

				for (BenutzerkontoBean aKonto : result) {
					key = generateIdentifier();
					while (map.containsKey(key)) {
						key = generateIdentifier();
					}
					map.put(key, aKonto);

					menu.append("<option value=\"" + key + "\">"
							+ aKonto.getBenutzer().getVorname() + " "
							+ aKonto.getBenutzer().getNachname()
							+ "</option>\n");
				}
				menu.append("</optgroup>");

			}
		} catch (DatenbankExceptions e) {
			// TODO Auto-generated catch block
			// FRAGE Verwendung von Systemexception korrekt?
			throw new SystemException();
		}
	}

	private static String generateIdentifier() {
		String id = KryptoUtil.getInstance().generatePasswort(10);
		return KryptoUtil.getInstance().hashPasswort(id);
	}

	/**
	 * Liefert das Passwort des EMailservers
	 * 
	 * @return Das Passwort
	 */
	public static final String getPwd() {
		if (pwd == null) {
			pwd = Config.getProperty(Config.Felder.RELEASE_MAIL_PASSWORD);
			System.out.println("Lade pwd: " + pwd);
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
			System.out.println("Lade server: " + server);
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
			System.out.println("Lade user: " + user);
		}
		return user;
	}

}